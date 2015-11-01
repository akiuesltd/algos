package com.akieus.stst.lowgc;

import com.akieus.stst.Configuration;
import com.akieus.stst.FxPrice;
import com.akieus.stst.FxPriceImpl;
import com.akieus.stst.ReferenceRateCalculator;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import static com.akieus.stst.lowgc.Market.ALL_MARKETS;
import static com.akieus.stst.lowgc.Market.calculateMarketId;

public class LowGCReferenceRateCalculator implements ReferenceRateCalculator {

    private static final FxPrice STALE_PRICE = new FxPriceImpl(0.0d, 0.0d, true, null, null);

    private final Percentile percentile = new Percentile();

    // IDs of configured markes (3 to 15 markets typically)
    private int[] configuredMarkets = {};

    // an array that can hold mid-prices for {@link ALL_MARKETS} but only has valid values
    // at indexes that correspond to configuredMarkets.
    private double[] allMidPrices = new double[ALL_MARKETS.length];
    private double[] validMidPrices = {};

    @Override
    public FxPrice calculate() {
        int count = collectValidPrices();
        if (count == 0) {
            // assumed that we should return stale price if there are no prices at all
            // besides (of course) if all prices are stale.
            return STALE_PRICE;
        }

        return calculateMedianOfValidPrices(count);
    }

    private FxPrice calculateMedianOfValidPrices(int count) {
        double median = percentile.evaluate(validMidPrices, 0, count, 50); // median is 50th percentile
        // defensive copy, object creation possibly avoidable if the API returned just the price.
        return new FxPriceImpl(median);
    }

    /**
     * Copies all valid mid-prices to validMidPrices array, and returns the count.
     * Only values up to the index of 'count' are valid, rest are garbage that's
     * not been cleaned up.
     */
    private int collectValidPrices() {
        int count = 0;
        for (int configuredMarket : configuredMarkets) {
            double midPrice = allMidPrices[configuredMarket];
            if (midPrice != 0.0d) {
                validMidPrices[count++] = midPrice;
            }
        }
        return count;
    }

    @Override
    public void onFxPrice(FxPrice fxPrice) {
        int marketId = calculateMarketId(fxPrice.getSource(), fxPrice.getProvider());
        if (fxPrice.isStale()) {
            allMidPrices[marketId] = 0.0d;
        } else {
            double mid = (fxPrice.getBid() + fxPrice.getOffer()) / 2;
            allMidPrices[marketId] = mid;
        }
    }

    private Market getMarket(FxPrice fxPrice) {
        return new Market(fxPrice.getSource(), fxPrice.getProvider());
    }

    @Override
    public void onConfiguration(Configuration configuration) {
        // reset all prices
        for (int i = 0; i < allMidPrices.length; i++) {
            allMidPrices[i] = 0.0d;
        }

        // reset configured markets
        validMidPrices = new double[configuration.getSize()];
        configuredMarkets = new int[configuration.getSize()];
        for (int i = 0; i < configuration.getSize(); i++) {
            configuredMarkets[i] = calculateMarketId(configuration.getSource(i), configuration.getProvider(i));
            validMidPrices[i] = 0.0d;
        }
    }
}