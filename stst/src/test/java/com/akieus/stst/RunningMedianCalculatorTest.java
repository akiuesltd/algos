package com.akieus.stst;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

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

    @Test
    public void testByInsertingRandomNumbers() {
        for (int i = 0; i < 100; i++) {
            randomInsertionTest();
        }
    }

    private void randomInsertionTest() {
        int n = 16;

        RunningMedianCalculator calculator = new RunningMedianCalculator(n);
        Random random = new Random();
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = random.nextDouble();
            calculator.add(values[i]);

            double myMedian = calculator.getMedian();
            double median = apacheMedian(values, i + 1);
            assertSame(median, myMedian);
        }
    }

    @Test
    public void testByUpdatingRandomNumbers() {
        for (int i = 0; i < 100; i++) {
            randomUpdateTest();
        }
    }

    private void randomUpdateTest() {
        int n = 16;

        RunningMedianCalculator calculator = new RunningMedianCalculator(n);
        Random random = new Random();
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = random.nextDouble();
            calculator.add(values[i]);
        }

        double myMedian = calculator.getMedian();
        double median = apacheMedian(values, n);
        assertSame(median, myMedian);

        // now update numbers at randomly chosen indexes
        for (int i = 0; i < n; i++) {
            int idx = random.nextInt(n);

            double newValue = random.nextDouble();
            double oldValue = values[idx];
            calculator.replace(oldValue, newValue);
            values[idx] = newValue;

            myMedian = calculator.getMedian();
            median = apacheMedian(values, n);
            assertSame(median, myMedian);
        }

    }

    private void assertSame(final double median, final double myMedian) {
        if (Double.isNaN(median)) {
            assertThat(Double.isNaN(myMedian), is(true));
        } else {
            assertThat(myMedian, is(closeTo(median, 0.0001)));
        }
    }

    private double apacheMedian(double[] values, int count) {
        return new Percentile().evaluate(values, 0, count, 50);
    }

    private RunningMedianCalculator aRunningCalculator() {
        return new RunningMedianCalculator(8);
    }
}
