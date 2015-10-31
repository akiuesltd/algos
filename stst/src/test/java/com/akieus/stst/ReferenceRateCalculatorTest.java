package com.akieus.stst;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class ReferenceRateCalculatorTest {

    @Test
    public void stalePriceReturnedIfNoConfiguration() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        assertThat(calculator.calculate(), is(notNullValue()));
        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void stalePriceReturnedIfConfiguredButNoPrices() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        calculator.onConfiguration(simpleConfiguration());

        assertThat(calculator.calculate(), is(notNullValue()));
        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void stalePriceReturnedIfASingleStalePrice() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        calculator.onConfiguration(simpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(Double.NaN, Double.NaN, true, PriceSource.SOURCE1, PriceProvider.PROVIDER1));

        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void validPriceReturnedIfASingleValidPrice() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        calculator.onConfiguration(simpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, false, PriceSource.SOURCE1, PriceProvider.PROVIDER1));

        assertThat(calculator.calculate().isStale(), is(false));
    }

    @Test
    public void midPriceReturnedIfASingleValidPrice() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        calculator.onConfiguration(simpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));

        // validate both bid & offer
        assertThat(calculator.calculate().getBid(), is(closeTo(1.15, 0.01)));
        assertThat(calculator.calculate().getOffer(), is(closeTo(1.15, 0.01)));
    }

    @Test
    public void stalePriceReturnedIfASingleValidPriceBecomesStale() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        calculator.onConfiguration(simpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        assertThat(calculator.calculate().isStale(), is(false));

        // now make the price from same market stale
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, true, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        // must return stale price now
        assertThat(calculator.calculate().isStale(), is(true));
    }

    @Test
    public void medianOfMidPricesReturnedIfThreePrices() {
        ReferenceRateCalculatorImpl calculator = newCalculator();
        calculator.onConfiguration(simpleConfiguration());
        calculator.onFxPrice(new FxPriceImpl(1.1, 1.2, PriceSource.SOURCE1, PriceProvider.PROVIDER1));
        calculator.onFxPrice(new FxPriceImpl(1.3, 1.4, PriceSource.SOURCE1, PriceProvider.PROVIDER2));
        calculator.onFxPrice(new FxPriceImpl(1.5, 1.6, PriceSource.SOURCE2, null));

        assertThat(calculator.calculate().getBid(), is(closeTo(1.35, 0.01)));
    }

    private ReferenceRateCalculatorImpl newCalculator() {
        return new ReferenceRateCalculatorImpl();
    }

    private Configuration simpleConfiguration() {
        return new ConfigurationImpl(new Market[]{
                new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER1),
                new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER2),
                new Market(PriceSource.SOURCE2, null),
                new Market(PriceSource.SOURCE3, null)
        });
    }
}
