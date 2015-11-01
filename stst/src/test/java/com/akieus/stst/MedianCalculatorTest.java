package com.akieus.stst;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.junit.Test;

import java.util.Random;

import static com.akieus.stst.MedianCalculator.calculateMedian;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class MedianCalculatorTest {

    @Test
    public void medianOfSingleElementShouldReturnFirstElement() {
        assertThat(calculateMedian(new double[]{1.1, 1.2}, 1), is(closeTo(1.1, 0.01)));
    }

    @Test
    public void checkMedianOfTwoElementSortedArray() {
        assertThat(calculateMedian(new double[]{1.1, 1.2}, 2), is(closeTo(1.15, 0.01)));
    }

    @Test
    public void checkMedianOfTwoElementUnsortedArray() {
        assertThat(calculateMedian(new double[]{1.2, 1.1}, 2), is(closeTo(1.15, 0.01)));
    }

    @Test
    public void threeElementUnSortedArray() {
        assertThat(calculateMedian(new double[]{1.2, 1.1, 1.3}, 3), is(closeTo(1.2, 0.01)));
    }

    @Test
    public void fourElementArray() {
        assertThat(calculateMedian(new double[]{1.2, 1.1, 1.3, 1.4}, 4), is(closeTo(1.25, 0.01)));
    }

    @Test
    public void medianOfTwoElementsOnFourElementArray() {
        assertThat(calculateMedian(new double[]{1.2, 1.1, 1.3, 1.4}, 2), is(closeTo(1.15, 0.01)));
    }

    @Test
    public void randomlyGeneratedTest() {
        for (int i = 0; i < 100; i++) {
            singleRandomTest();
        }
    }

    private void singleRandomTest() {
        Random random = new Random();
        int n = 16;
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = random.nextDouble();
        }

        int count = random.nextInt(n);

        double median = apacheMedian(values, count);
        double myMedian = calculateMedian(values, count);
        if (Double.isNaN(median)) {
            assertThat(Double.isNaN(myMedian), is(true));
        } else {
            assertThat(myMedian, is(closeTo(median, 0.0001)));
        }

    }

    private double apacheMedian(double[] values, int count) {
        return new Percentile().evaluate(values, 0, count, 50);
    }
}
