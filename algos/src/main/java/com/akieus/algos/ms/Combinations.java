package com.akieus.algos.ms;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author aks
 * @since 12/08/15
 */
public class Combinations {

    public static void main(String[] args) {
        LinkedList<String> input = toLinkedList(inputString());
        LinkedList<String> output = new LinkedList<>();

        String first = input.remove(0);
        for (int i=0; i<first.length(); i++) {
            output.addLast(first.charAt(i) + "");
        }

        generateCombinations(output, input);

        for (String str : output) {
            System.out.print(str + ", ");
        }
    }

    private static void generateCombinations(LinkedList<String> output, LinkedList<String> input) {
        if (input.isEmpty()) {
            return;
        }

        String current = input.removeFirst();

        int outSize = output.size();
        for (int i=0; i<outSize; i++) {
            String outI = output.removeFirst();
            for (char charJ : current.toCharArray()) {
                output.addLast(outI + charJ);
            }
        }
        generateCombinations(output, input);
    }

    private static LinkedList<String> toLinkedList(String[] arr) {
        LinkedList<String> list = new LinkedList<>();
        for (String str : arr) {
            list.add(str);
        }
        return list;
    }

    private static String[] inputString() {
        return new String[]{"MORGAN", "STANLEY", "INVESTMENT", "BANK"};
    }
}
