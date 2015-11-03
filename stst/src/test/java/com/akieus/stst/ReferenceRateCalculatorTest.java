package com.akieus.stst;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class ReferenceRateCalculatorTest {
    private static final Market TEST_MARKET_1 = new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_2 = new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER2);
    private static final Market TEST_MARKET_3 = new Market(PriceSource.SOURCE2, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_4 = new Market(PriceSource.SOURCE3, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_5 = new Market(PriceSource.SOURCE4, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_6 = new Market(PriceSource.SOURCE5, PriceProvider.NULL_PROVIDER);

    @Test
    public void stalePriceReturnedIfNoConfiguration() {
        ReferenceRateCalculator calculator = newCalculator();
        assertThat(calculator.calculate(), is(notNullValue()));
        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void stalePriceReturnedIfConfiguredButNoPrices() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());

        assertThat(calculator.calculate(), is(notNullValue()));
        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void stalePriceReturnedIfASingleStalePrice() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(Double.NaN, Double.NaN, true, PriceSource.SOURCE1, PriceProvider.PROVIDER1));

        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void validPriceReturnedIfASingleValidPrice() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, false, PriceSource.SOURCE1, PriceProvider.PROVIDER1));

        assertThat(calculator.calculate().isStale(), is(false));
        // validate both bid & offer
        assertThat(calculator.calculate().getBid(), is(closeTo(1.15, 0.01)));
        assertThat(calculator.calculate().getOffer(), is(closeTo(1.15, 0.01)));
    }

    @Test
    public void stalePriceReturnedIfASingleValidPriceBecomesStale() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        assertThat(calculator.calculate().isStale(), is(false));

        // now make the price from same market stale
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, true, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        // must return stale price now
        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void validPriceReturnedIfASingleStalePriceBecomesValid() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        // valid price
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        // make it stale & check
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, true, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        assertThat(calculator.calculate().isStale(), is(true));
        // make it valid again & check
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        assertThat(calculator.calculate().isStale(), is(false));
    }

    @Test
    public void medianOfMidPricesReturnedIfThreePrices() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.3, 1.4, PriceSource.SOURCE1, PriceProvider.PROVIDER2));
        calculator.onFxPrice(new FxPriceImpl(1.5, 1.6, PriceSource.SOURCE2, PriceProvider.NULL_PROVIDER));

        assertThat(calculator.calculate().getBid(), is(closeTo(1.35, 0.01)));
    }

    @Test
    public void medianCalculatedCorrectlyIfEvenPricePoints() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.3, 1.4, PriceSource.SOURCE1, PriceProvider.PROVIDER2));

        assertThat(calculator.calculate().getBid(), is(closeTo(1.25, 0.01)));
    }

    @Test
    public void medianCalculatedCorrectlyIfDuplicatePricePoints() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.3, 1.4, PriceSource.SOURCE1, PriceProvider.PROVIDER2));

        assertThat(calculator.calculate().getBid(), is(closeTo(1.15, 0.01)));
    }

    @Test
    public void resettingConfigurationResetsPrices() {
        ReferenceRateCalculator calculator = newCalculator();
        calculator.onConfiguration(aSimpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.3, 1.4, PriceSource.SOURCE1, PriceProvider.PROVIDER2));
        assertThat(calculator.calculate().isStale(), is(false));

        calculator.onConfiguration(anotherSimpleConfiguration());
        assertThat(calculator.calculate().isStale(), is(true));
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE4, PriceProvider.PROVIDER1));
        assertThat(calculator.calculate().getBid(), is(closeTo(1.15, 0.01)));
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
}
