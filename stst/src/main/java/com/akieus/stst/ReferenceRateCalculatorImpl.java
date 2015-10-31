package com.akieus.stst;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReferenceRateCalculatorImpl implements ReferenceRateCalculator {

    private static final FxPrice STALE_PRICE = new FxPriceImpl(Double.NaN, Double.NaN, true, null, null);

    Percentile percentile = new Percentile();
    private Map<Market, FxPrice> prices = new HashMap<>();

    @Override
    public FxPrice calculate() {
        if (prices.isEmpty()) {
            // assumed that it's okay to return stale price if there are no prices at all
            // besides (of course) if all prices are stale.
            return STALE_PRICE;
        }

        return calculateMedian(prices.values());
    }

    private FxPrice calculateMedian(Collection<FxPrice> priceSet) {
        double[] mids = new double[priceSet.size()];
        int i = 0;
        Iterator<FxPrice> itr = priceSet.iterator();
        while (itr.hasNext()) {
            FxPrice fxPrice = itr.next();
            mids[i++] = (fxPrice.getBid() + fxPrice.getOffer()) / 2;
        }

        double median = percentile.evaluate(mids, 50); // median is 50th percentile
        return new FxPriceImpl(median);
    }

    @Override
    public void onFxPrice(FxPrice fxPrice) {
        Market market = getMarket(fxPrice);
        if (fxPrice.isStale()) {
            prices.remove(market);
        } else {
            prices.put(market, fxPrice);
        }
    }

    private Market getMarket(FxPrice fxPrice) {
        return new Market(fxPrice.getSource(), fxPrice.getProvider());
    }

    @Override
    public void onConfiguration(Configuration configuration) {
        prices.clear();
    }
}