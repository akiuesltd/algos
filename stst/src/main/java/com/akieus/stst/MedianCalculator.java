package com.akieus.stst;

import java.util.Arrays;

/**
 * @author aks
 * @since 01/11/15
 */
public class MedianCalculator {

    public static double calculateMedian(final double[] values, final int count) {
        assert count >= 0 && count <= values.length;

        if (count == 0) {
            return Double.NaN;
        } else if (count == 1) {
            return values[0];
        }

        Arrays.sort(values, 0, count);
        int medianPoint = count >> 1;
        if (isEven(count)) {
            return (values[medianPoint] + values[medianPoint - 1]) / 2;
        } else {
            return values[medianPoint];
        }
    }

    public static boolean isEven(int x) {
        return (x & 1) == 0;
    }

}
