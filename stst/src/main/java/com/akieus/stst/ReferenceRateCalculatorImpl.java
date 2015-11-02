package com.akieus.stst;

import com.akieus.stst.collections.RunningMedianCalculator;

import static com.akieus.stst.Market.calculateMarketId;

public class ReferenceRateCalculatorImpl implements ReferenceRateCalculator {

    private static final int MAX_MARKETS = 16;
    private static final FxPrice STALE_PRICE = new FxPriceImpl(Double.NaN, Double.NaN, true, null, null);

    private double[] prices = new double[Market.ALL_MARKETS.length];
    private RunningMedianCalculator calculator = new RunningMedianCalculator(MAX_MARKETS);

    @Override
    public FxPrice calculate() {
        double median = calculator.getMedian();
        if (Double.isNaN(median)) {
            return STALE_PRICE;
        }

        return new FxPriceImpl(median);
    }

    @Override
    public void onFxPrice(final FxPrice fxPrice) {
        int marketId = calculateMarketId(fxPrice.getSource(), fxPrice.getProvider());

        double oldPrice = prices[marketId];
        double newPrice = fxPrice.isStale() ? Double.NaN : midPrice(fxPrice);

        prices[marketId] = newPrice;

        // TODO rewrite
        if (Double.isNaN(oldPrice)) {
            if (!Double.isNaN(newPrice)) {
                calculator.add(newPrice);
            }
        } else {
            if (Double.isNaN(newPrice)) {
                calculator.remove(oldPrice);
            } else {
                calculator.replace(oldPrice, newPrice);
            }
        }
    }


    @Override
    public void onConfiguration(final Configuration configuration) {
        if (configuration.getSize() > MAX_MARKETS) {
            throw new IllegalArgumentException("Cannot configured more than " + MAX_MARKETS + " markets");
        }

        for (int i = 0; i < prices.length; i++) {
            prices[i] = Double.NaN;
        }
        calculator.reset();
    }

    private double midPrice(final FxPrice fxPrice) {
        return (fxPrice.getBid() + fxPrice.getOffer()) / 2;
    }
}