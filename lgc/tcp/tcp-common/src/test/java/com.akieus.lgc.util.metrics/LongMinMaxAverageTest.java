package com.akieus.lgc.util.metrics;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by aks on 19/03/2016.
 */
public class LongMinMaxAverageTest {

    @Test(expected = IllegalArgumentException.class)
    public void warmupMustBeNonNegative() {
        new LongMinMaxAverage(-1);
    }

    @Test
    public void testNoValue() {
        LongMinMaxAverage metrics = new LongMinMaxAverage(0);
        assertThat(metrics.count(), is(0));
        assertThat(metrics.max(), is(Long.MIN_VALUE));
        assertThat(metrics.min(), is(Long.MAX_VALUE));
        assertThat(metrics.avg(), is(0L));
    }

    @Test
    public void testOneValue() {
        LongMinMaxAverage metrics = new LongMinMaxAverage(0);
        metrics.add(1L);
        assertThat(metrics.count(), is(1));
        assertThat(metrics.max(), is(1L));
        assertThat(metrics.min(), is(1L));
        assertThat(metrics.avg(), is(1L));
    }

    @Test
    public void testTwoValues() {
        LongMinMaxAverage metrics = new LongMinMaxAverage(0);
        metrics.add(1L);
        metrics.add(3L);
        assertThat(metrics.count(), is(2));
        assertThat(metrics.max(), is(3L));
        assertThat(metrics.min(), is(1L));
        assertThat(metrics.avg(), is(2L));
    }

    @Test
    public void testThreeValues() {
        LongMinMaxAverage metrics = new LongMinMaxAverage(0);
        metrics.add(1L);
        metrics.add(3L);
        metrics.add(5L);
        assertThat(metrics.count(), is(3));
        assertThat(metrics.max(), is(5L));
        assertThat(metrics.min(), is(1L));
        assertThat(metrics.avg(), is(3L));
    }

    @Test
    public void warmupIsExcluded() {
        LongMinMaxAverage metrics = new LongMinMaxAverage(1);
        metrics.add(1L);
        metrics.add(3L);
        metrics.add(5L);
        assertThat(metrics.count(), is(3));
        assertThat(metrics.measures(), is(2));
        assertThat(metrics.max(), is(5L));
        assertThat(metrics.min(), is(3L));
        assertThat(metrics.avg(), is(4L));
    }
}
