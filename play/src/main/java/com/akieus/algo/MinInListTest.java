package com.akieus.algo;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static junit.framework.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class MinInListTest {
    @Test(expected = IllegalArgumentException.class)
    public void nullList() {
        new MinInList().min(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyList() {
        new MinInList().min(Collections.<Integer>emptyList());
    }

    public void oneItem() {
        assertEquals(1, new MinInList().min(Arrays.asList(1)));
    }

    public void twoItems() {
        assertEquals(1, new MinInList().min(Arrays.asList(1, 2)));
    }

    public void twoItemsReverse() {
        assertEquals(1, new MinInList().min(Arrays.asList(2, 1)));
    }

    public void fiveItems() {
        assertEquals(1, new MinInList().min(Arrays.asList(0, 1, 2, 3, 4)));
    }

    public void fiveItemsRandom() {
        assertEquals(1, new MinInList().min(Arrays.asList(5, 2, 1, 3, 4)));
    }
}
