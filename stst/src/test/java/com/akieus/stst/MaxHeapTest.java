package com.akieus.stst;

import com.akieus.stst.collections.AbstractHeap;
import com.akieus.stst.collections.MaxHeap;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class MaxHeapTest {

    @Test
    public void testInsertions() {
        int n = 16;
        AbstractHeap maxHeap = newHeap();

        Random random = new Random();
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = random.nextDouble();
            maxHeap.add(values[i]);
        }
    }

    @Test
    public void testRemovals() {
        int n = 16;
        AbstractHeap maxHeap = newHeap();

        Random random = new Random();
        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = (int) (random.nextDouble() * 100);
            maxHeap.add(values[i]);
        }

        int[] randomIndices = randomIndices(n);
        for (int i = 0; i < n; i++) {
            double valueToRemove = values[randomIndices[i]];
            int removedIdx = maxHeap.remove(valueToRemove);
            assertThat(removedIdx, is(not(0)));
        }
    }

    private int[] randomIndices(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        shuffle(arr);
        return arr;
    }

    private void shuffle(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private MaxHeap newHeap() {
        return new MaxHeap(16);
    }

}
