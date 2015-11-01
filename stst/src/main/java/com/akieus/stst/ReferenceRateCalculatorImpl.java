package com.akieus.stst;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.Arrays;

import static com.akieus.stst.Market.calculateMarketId;

public class ReferenceRateCalculatorImpl implements ReferenceRateCalculator {

    private static final FxPrice STALE_PRICE = new FxPriceImpl(Double.NaN, Double.NaN, true, null, null);

    private final Percentile percentile = new Percentile();

    private double[] allMidPrices = new double[Market.ALL_MARKETS.length];
    private int[] configuredMarkets = {};
    private double[] validMidPrices = {};

    @Override
    public FxPrice calculate() {
        int count = collectValidPrices();
        if (count == 0) {
            return STALE_PRICE;
        }

        double median = MedianCalculator.calculateMedian(validMidPrices, count);
        return new FxPriceImpl(median);
    }

    @Override
    public void onFxPrice(final FxPrice fxPrice) {
        int marketId = calculateMarketId(fxPrice.getSource(), fxPrice.getProvider());
        allMidPrices[marketId] = fxPrice.isStale() ? Double.NaN : midPrice(fxPrice);
    }

    @Override
    public void onConfiguration(final Configuration configuration) {
        for (int i = 0; i < allMidPrices.length; i++) {
            allMidPrices[i] = Double.NaN;
        }

        validMidPrices = new double[configuration.getSize()];
        configuredMarkets = new int[configuration.getSize()];
        for (int i = 0; i < configuration.getSize(); i++) {
            configuredMarkets[i] = calculateMarketId(configuration.getSource(i), configuration.getProvider(i));
            validMidPrices[i] = Double.NaN;
        }
    }

    private double midPrice(final FxPrice fxPrice) {
        return (fxPrice.getBid() + fxPrice.getOffer()) / 2;
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
            if (!Double.isNaN(midPrice)) {
                validMidPrices[count++] = midPrice;
            }
        }
        return count;
    }
}