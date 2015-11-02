package com.akieus.stst;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReferenceRateCalculatorSoakTest {
    private static final Market TEST_MARKET_1 = new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_2 = new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER2);
    private static final Market TEST_MARKET_3 = new Market(PriceSource.SOURCE2, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_4 = new Market(PriceSource.SOURCE3, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_5 = new Market(PriceSource.SOURCE4, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_6 = new Market(PriceSource.SOURCE5, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_7 = new Market(PriceSource.SOURCE6, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_8 = new Market(PriceSource.SOURCE7, PriceProvider.PROVIDER2);
    private static final Market TEST_MARKET_9 = new Market(PriceSource.SOURCE8, PriceProvider.PROVIDER3);
    private static final Market TEST_MARKET_10 = new Market(PriceSource.SOURCE9, PriceProvider.PROVIDER4);
    private static final Market TEST_MARKET_11 = new Market(PriceSource.SOURCE10, PriceProvider.PROVIDER5);
    private static final Market TEST_MARKET_12 = new Market(PriceSource.SOURCE11, PriceProvider.PROVIDER6);
    private static final Market TEST_MARKET_13 = new Market(PriceSource.SOURCE12, PriceProvider.PROVIDER7);
    private static final Market TEST_MARKET_14 = new Market(PriceSource.SOURCE13, PriceProvider.PROVIDER8);
    private static final Market TEST_MARKET_15 = new Market(PriceSource.SOURCE14, PriceProvider.PROVIDER9);

    int x = 0;

    @Test
    public void soakTestWithRandomPrices() {
        final ReferenceRateCalculator calculator = newCalculator();
        Market[] configuredMarkets = new Market[]{
                TEST_MARKET_1, TEST_MARKET_2, TEST_MARKET_3, TEST_MARKET_4, TEST_MARKET_5,
                TEST_MARKET_6, TEST_MARKET_7, TEST_MARKET_8, TEST_MARKET_9, TEST_MARKET_10,
                TEST_MARKET_11, TEST_MARKET_12, TEST_MARKET_13, TEST_MARKET_14, TEST_MARKET_15
        };
        double[] mids = new double[configuredMarkets.length];

        resetConfigurationAndPopulateAllMarketPrices(calculator, configuredMarkets, mids);

        for (int i = 0; i < 1000 * 1000; i++) {
            if (i / 1000 == 0) {
                resetConfigurationAndPopulateAllMarketPrices(calculator, configuredMarkets, mids);
            }
            sendRandomUpdateAndCheck(calculator, configuredMarkets, mids);
        }

    }

    private void sendRandomUpdateAndCheck(final ReferenceRateCalculator calculator, final Market[] configuredMarkets, final double[] mids) {
        int randomMarket = ThreadLocalRandom.current().nextInt(15);

        double bid = nonZeroRandomDouble();
        double offer = nonZeroRandomDouble();
        double mid = mid(bid, offer);

        mids[randomMarket] = mid;
        calculator.onFxPrice(new FxPriceImpl(bid, offer, configuredMarkets[randomMarket].getSource(), configuredMarkets[randomMarket].getProvider()));
        assertSame(calculator.calculate().getBid(), new Percentile().evaluate(mids, 50.0d));
    }

    private double[] resetConfigurationAndPopulateAllMarketPrices(final ReferenceRateCalculator calculator, final Market[] configuredMarkets, final double[] mids) {
        calculator.onConfiguration(new ConfigurationImpl(configuredMarkets));
        for (int i = 0; i < configuredMarkets.length; i++) {
            double bid = nonZeroRandomDouble();
            double offer = nonZeroRandomDouble();
            double mid = mid(bid, offer);
            mids[i] = mid;

            calculator.onFxPrice(new FxPriceImpl(bid, offer, configuredMarkets[i].getSource(), configuredMarkets[i].getProvider()));
        }
        assertSame(calculator.calculate().getBid(), new Percentile().evaluate(mids, 50.0d));
        return mids;
    }

    private void assertSame(final double median, final double myMedian) {
        if (Double.isNaN(median)) {
            assertThat(Double.isNaN(myMedian), is(true));
        } else {
            assertThat(myMedian, is(IsCloseTo.closeTo(median, 0.0001)));
        }
    }

    private double mid(final double bid, final double offer) {
        return (bid + offer) / 2;
    }

    private ReferenceRateCalculatorImpl newCalculator() {
        return new ReferenceRateCalculatorImpl();
    }

    private Configuration aSimpleConfiguration() {
        return new ConfigurationImpl(new Market[]{TEST_MARKET_1, TEST_MARKET_2, TEST_MARKET_3, TEST_MARKET_4});
    }

    private Configuration anotherSimpleConfiguration() {
        return new ConfigurationImpl(new Market[]{TEST_MARKET_5, TEST_MARKET_6});
    }

    private double nonZeroRandomDouble() {
        return ++x;
//        while (true) {
//            double random = ThreadLocalRandom.current().nextDouble();
//            if (random != 0.0) {
//                return (int) (random * 10);
//            }
//        }
    }
}
