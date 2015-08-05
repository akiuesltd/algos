package com.akieus.commerz;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by aks on 7/28/15.
 */
public class TestSingleton {
    public static void main(String[] args) throws InterruptedException {
        Singleton s = Singleton.getInstance();
        ReferenceQueue<Singleton> referenceQueue = new ReferenceQueue<Singleton>();
        WeakReference<Singleton> w = new WeakReference<Singleton>(s, referenceQueue);
        System.out.println(w.get());
        s = null;

        System.gc();
        for (int i=0; i<100000; i++) {
            new Object();
        }
        System.gc();
        for (int i=0; i<100000; i++) {
            new Object();
        }
        System.gc();

        referenceQueue.remove();

        System.out.println(w.get());
    }
}
