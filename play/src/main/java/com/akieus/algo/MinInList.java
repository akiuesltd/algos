package com.akieus.algo;

import java.util.Iterator;
import java.util.List;

public class MinInList {

    public int min(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Iterator<Integer> itr = list.iterator();
        int min = itr.next();
        while (itr.hasNext()) {
            Integer val = itr.next();
            if (val != null && val.intValue() < min) {
                min = val.intValue();
            }
        }

        return min;
    }

    public static void main(String[] args) {
        new MinInList().min(null);
    }
}
