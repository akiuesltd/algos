package com.akieus.stst;

import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import static com.akieus.stst.MedianCalculator.calculateMedian;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class MedianCalculatorTest {

    @Test
    public void medianOfZeroElementsShouldIsUndefined() {
        assertThat(Double.isNaN(calculateMedian(new double[]{1.1, 1.2}, 0)), is(true));
    }

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

}
