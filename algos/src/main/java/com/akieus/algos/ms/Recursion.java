package com.akieus.algos.ms;

/**
 * Created by aks on 07/08/15.
 */
public class Recursion {

    public static void main(String[] args) {
        f1(6); System.out.println();
        f2(6); System.out.println();
        f4(6); System.out.println();
    }

    private static void f1(int n) {
        if (n > 2) {
            f1(n - 1);
        }
        System.out.print(n + ", ");
    }

    private static void f2(int n) {
        if (n > 2) {
            f2(--n);
        }
        System.out.print(n + ", ");
    }

    private static void f3(int n) {
        if (n > 2) {
            f3(n--);
        }
        System.out.println(n + ", ");
    }

    private static void f4(int n) {
        System.out.print(n + ", ");
        if (n > 2) {
            f4(n - 1);
        }
    }
}
