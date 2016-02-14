package com.akieus.timers;

public class NanoVsMillis {

    public static void main(String[] args) {
        int count = 10 * 1000 * 1000;
        long[] times = new long[count];

        long t1 = System.nanoTime();

        for (int i = 0; i < count; i++) {
            times[i] = System.nanoTime();
        }

        long t2 = System.nanoTime();

        for (int i = 0; i < count; i++) {
            times[i] = System.currentTimeMillis();
        }

        long t3 = System.nanoTime();

        System.out.println("Total time nano  : " + ((t2 - t1) / (1000 * 1000)) + "ms");
        System.out.println("Total time millis: " + ((t3 - t2) / (1000 * 1000)) + "ms");
        System.out.println("");
        System.out.println("Avg time nano  : " + ((t2 - t1) / count) + "ns");
        System.out.println("Avg time millis: " + ((t3 - t2) / count) + "ns");
    }

}
