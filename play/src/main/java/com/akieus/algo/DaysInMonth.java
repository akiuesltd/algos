package com.akieus.algo;

public class DaysInMonth {

    private static int DAY_COUNT[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static void main(String[] args) {
        for (int i = 0; i < DAY_COUNT.length; i++) {
            if (dayCount(i + 1) != DAY_COUNT[i]) {
                System.out.println("Failed for: " + (i + 1) + ", returned: " + dayCount(i + 1));
                return;
            }
        }
        System.out.println("kewl.");
    }

    public static int dayCount(int n) {
        // return m==2?28:m<8?m%2==0?30:31:m%2==0?31:30;
        // return n==2?28:n%2==0?n<8?30:31:n<8?31:30;
        // boolean b=n%2==0;return n==2?28:b?n<8?30:31:n<8?31:30;
        return 28 + (n == 2 ? 0 : n % 2 == 0 ? n < 8 ? 2 : 3 : n < 8 ? 3 : 2);
    }

}
