package com.akieus.lgc.util.metrics;

import org.slf4j.Logger;

import static com.google.common.base.Preconditions.checkArgument;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by aks on 19/03/2016.
 */
public class LongMinMaxAverage {
    private static final Logger LOG = getLogger(LongMinMaxAverage.class);

    private final int warmup;

    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private long sum;
    private int count;

    public LongMinMaxAverage(int warmup) {
        checkArgument(warmup >= 0);
        this.warmup = warmup;
    }

    public void add(long value) {
        if (++count > warmup) {
            LOG.trace("{}", value);
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }
    }

    public int getWarmup() {
        return warmup;
    }

    public long min() {
        return min;
    }

    public long max() {
        return max;
    }

    public long avg() {
        return measures() == 0 ? 0 : sum / measures();
    }

    public int count() {
        return count;
    }

    public int measures() {
        return Math.abs(count - warmup);
    }
}
