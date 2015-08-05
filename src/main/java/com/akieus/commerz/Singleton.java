package com.akieus.commerz;

/**
 * Created by aks on 7/28/15.
 */
public class Singleton {

    private static final Singleton INSTANCE =  new Singleton();
    private Singleton() {
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
