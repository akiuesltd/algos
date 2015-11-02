package com.akieus.stst.collections;

/**
 * @author aks
 * @since 01/11/15
 */
public class RunningMedianCalculator {

    private BinaryMaxHeap maxHeap = new BinaryMaxHeap(8);
    private BinaryMinHeap minHeap = new BinaryMinHeap(8);

    public RunningMedianCalculator() {

    }

    public void add(double value) {
        if (maxHeap.isEmpty() && minHeap.isEmpty()) {
            maxHeap.add(value);
            return;
        } else if (maxHeap.size() + minHeap.size() == 1) {
            double current = maxHeap.isEmpty() ? minHeap.removeRoot() : maxHeap.removeRoot();
            if (value > current) {
                maxHeap.add(current);
                minHeap.add(value);
            } else {
                maxHeap.add(value);
                minHeap.add(current);
            }
            return;
        }

        if (value < maxHeap.root()) {
            maxHeap.add(value);
        } else {
            minHeap.add(value);
        }

        rebalance();
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
        if (maxHeap.size() == minHeap.size()) {
            return (maxHeap.root() + minHeap.root()) / 2;
        } else if (maxHeap.size() > minHeap.size()) {
            return maxHeap.root();
        } else {
            return minHeap.root();
        }
    }
}
