package com.akieus.algos.ubs;

/**
 * @author aks
 * @since 19/10/15
 */
public class IntParser {
    public static void main(String... args) {
        print(parse(9));
    }

    private static void print(int[] arr) {
        for (int a : arr) {
            System.out.print(a + ", ");
        }
        System.out.println();
    }

    private static int[] parse(int x) {
        int[] bits = new int[32];

        int mask = 1;
        for (int i = 31; i >= 0; i--) {
            if ((x & mask) == mask) {
                bits[i] = 1;
            }
            mask = mask << 1;
        }

        return bits;
    }
}
