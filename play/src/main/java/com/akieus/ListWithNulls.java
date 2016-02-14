package com.akieus;


import java.util.ArrayList;
import java.util.List;

public class ListWithNulls {

    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<Integer>();
        ints.add(null);
        int i = ints.get(0);
        System.out.println(i);
    }

}
