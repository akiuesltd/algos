package com.akieus.stst;

import com.akieus.stst.collections.RunningMedianCalculator;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.junit.Test;

import java.util.Random;

import static com.akieus.stst.MedianCalculator.calculateMedian;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class RunningMedianCalculatorTest {

    @Test
    public void shouldReturnNanIfNoValues() {
        RunningMedianCalculator calculator = aRunningCalculator();
        assertThat(Double.isNaN(calculator.getMedian()), is(true));

    }

    @Test
    public void shouldReturnTheValueIfSingleValue() {
        RunningMedianCalculator calculator = aRunningCalculator();
        calculator.add(1.1);
        assertThat(calculator.getMedian(), is(closeTo(1.1, 0.0001)));

    }

    @Test
    public void shouldReturnTheValueIfTwoSameValues() {
        RunningMedianCalculator calculator = aRunningCalculator();
        calculator.add(1.1);
        calculator.add(1.1);
        assertThat(calculator.getMedian(), is(closeTo(1.1, 0.0001)));

    }

    @Test
    public void shouldReturnMidValueIfTwoDistinctValues() {
        RunningMedianCalculator calculator = aRunningCalculator();
        calculator.add(1.1);
        calculator.add(1.2);
        assertThat(calculator.getMedian(), is(closeTo(1.15, 0.0001)));

    }

    @Test
    public void shouldReturnMedianValueIfThreeDistinctValuesUnordered() {
        RunningMedianCalculator calculator = aRunningCalculator();
        calculator.add(1.1);
        calculator.add(1.3);
        calculator.add(1.2);
        assertThat(calculator.getMedian(), is(closeTo(1.2, 0.0001)));

    }

    @Test
    public void removingAValueShouldHaveDesiredEffect() {
        RunningMedianCalculator calculator = aRunningCalculator();
        calculator.add(1.1);
        calculator.add(1.3);
        calculator.add(1.2);
        assertThat(calculator.getMedian(), is(closeTo(1.2, 0.0001)));
        calculator.remove(1.3);
        assertThat(calculator.getMedian(), is(closeTo(1.15, 0.0001)));

    }

    @Test
    public void replacingAValueShouldHaveDesiredEffect() {
        RunningMedianCalculator calculator = aRunningCalculator();
        calculator.add(1.1);
        calculator.add(1.3);
        calculator.add(1.2);
        assertThat(calculator.getMedian(), is(closeTo(1.2, 0.0001)));
        calculator.replace(1.3, 0.9);
        assertThat(calculator.getMedian(), is(closeTo(1.1, 0.0001)));

    }

    private RunningMedianCalculator aRunningCalculator() {
        return new RunningMedianCalculator(8);
    }

}
