package com.akieus.algos.coursera.unionfind;

import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author aks
 * @since 13/08/15
 */
public class QuickFind {
    public static void main(String[] args) {
        QuickFind qf = new QuickFind(10);
        qf.connect(8, 3);
        qf.connect(3, 4);
        qf.connect(9, 4);
        System.out.println(ArrayUtils.toString(qf.id));
        System.out.println(qf.isConnected(3, 9));
        System.out.println(qf.isConnected(3, 7));
    }

    private final int[] id;

    public QuickFind(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public void connect(int one, int two) {
        int iOne = id[one];
        int iTwo = id[two];

        if (iOne != iTwo) { // i.e. not already connected
            // connect them by setting iTwo wherever iOne appears.
            for (int i = 0; i < id.length; i++) {
                if (id[i] == iOne) {
                    id[i] = iTwo;
                }
            }
        }
    }

    public boolean isConnected(int one, int two) {
        return id[one] == id[two];
    }
}
