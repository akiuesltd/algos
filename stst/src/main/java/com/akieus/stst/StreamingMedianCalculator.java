package com.akieus.stst;

import com.akieus.stst.collections.MaxHeap;
import com.akieus.stst.collections.MinHeap;

/**
 * Keeps the data divided in two binary heaps (a max-heap and a min-heap) such
 * all values in the max-heap are <= median while all values in min-heap are >= median.
 * This allows median to be calculated quickly, but each update requires maintaining
 * the heaps.
 *
 * An alternate implementation that might be acceptable for small heaps is to
 * collect all values in an array, and sort the array on each getMedian() call.
 */
public class StreamingMedianCalculator {

    private final MaxHeap maxHeap;
    private final MinHeap minHeap;

    public StreamingMedianCalculator(int window) {
        this.maxHeap = new MaxHeap(window / 2 + 1);
        this.minHeap = new MinHeap(window / 2 + 1);
    }

    public void reset() {
        maxHeap.reset();
        minHeap.reset();
    }

    public void add(double value) {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            addFirstElement(value);
        } else if (maxHeap.size() + minHeap.size() == 1) {
            addSecondElement(value);
        } else {
            addToAppropriateHeap(value);
            rebalance();
        }
    }

    private void addFirstElement(final double value) {
        maxHeap.add(value);
    }

    private void addSecondElement(final double value) {
        double current = maxHeap.isEmpty() ? minHeap.removeRoot() : maxHeap.removeRoot();
        if (value > current) {
            maxHeap.add(current);
            minHeap.add(value);
        } else {
            maxHeap.add(value);
            minHeap.add(current);
        }
    }

    private void addToAppropriateHeap(final double value) {
        if (value < maxHeap.root()) {
            maxHeap.add(value);
        } else {
            minHeap.add(value);
        }
    }

    private void rebalance() {
        if (Math.abs(maxHeap.size() - minHeap.size()) > 1) {
            if (maxHeap.size() > minHeap.size()) {
                minHeap.add(maxHeap.removeRoot());
            } else {
                maxHeap.add(minHeap.removeRoot());
            }
        }
    }

    public void remove(double value) {
        if (removeIfPresent(value)) {
            rebalance();
        }
    }

    private boolean removeIfPresent(final double value) {
        int removeIndex = 0;
        if (!maxHeap.isEmpty() && value <= maxHeap.root()) {
            removeIndex = maxHeap.remove(value);
        } else if (!minHeap.isEmpty() && value >= minHeap.root()) {
            removeIndex = minHeap.remove(value);
        }
        return removeIndex != 0;
    }

    public void replace(double oldValue, double newValue) {
        removeIfPresent(oldValue);
        add(newValue);
    }

    public double getMedian() {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            return Double.NaN;
        } else if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.root() + minHeap.root()) / 2;
        } else if (maxHeap.size() > minHeap.size()) {
            return maxHeap.root();
        } else {
            return minHeap.root();
        }
    }
}
