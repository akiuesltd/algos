package com.akieus.kids;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TimesTableTest {

    private static final int MIN = 1;
    private static final int MAX = 12;

    public static void main(String... args) {
        args = new String[]{"10", "7", "8", "9"};

        if (args.length < 2) {
            System.out.println("Usage: java " + TimesTableTest.class.getCanonicalName() + " <Questions-Per-Number> <Number1> <Number2>");
            System.exit(-1);
        }

        int questionsPerNumber = Integer.parseInt(args[0]);
        int[] numbers = new int[args.length - 1];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(args[i + 1]);
        }
        List<Question> questions = new TimesTableTest().generateQuestions(numbers, questionsPerNumber);

        printQuestions(questions);
    }

    private static void printQuestions(List<Question> questions) {
        for (Question question : questions) {
            System.out.println(question.getOp1() + " "
                    + question.getOperand() + " "
                    + question.getOp2() + " = ");
        }
    }

    public List<Question> generateQuestions(int[] numbers, int questions) {
        List<Question> list = Lists.newLinkedList();
        for (int i = 0; i < numbers.length; i++) {
            list.addAll(generateQuestions(numbers[i], questions));
        }
        Collections.shuffle(list);
        return list;
    }

    public List<Question> generateQuestions(int number, int questions) {
        List<Question> list = Lists.newLinkedList();
        for (int i = 0; i < questions; i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                list.add(randomMultiplicationQuestion(number));
            } else {
                list.add(randomDivisionQuestion(number));
            }
        }
        return list;
    }

    private Question randomMultiplicationQuestion(final int number) {
        int op1 = number;
        int op2 = ThreadLocalRandom.current().nextInt(MAX) + 1;
        int ans = op1 * op2;
        return new Question(op1, Operand.MULTIPLY, op2, ans);
    }

    private Question randomDivisionQuestion(final int number) {
        int op1 = number * (ThreadLocalRandom.current().nextInt(MAX) + 1);
        int op2 = number;
        int ans = op1 / op2;
        return new Question(op1, Operand.DIVIDE, op2, ans);
    }

    private enum Operand {
        MULTIPLY("*"),
        DIVIDE("÷");

        private final String code;

        private Operand(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    private class Question {
        private int op1;
        private int op2;
        private int ans;
        private Operand operand;

        public Question(final int op1, final Operand operand, final int op2, final int ans) {
            this.op1 = op1;
            this.op2 = op2;
            this.ans = ans;
            this.operand = operand;
        }

        public int getOp1() {
            return op1;
        }

        public int getOp2() {
            return op2;
        }

        public int getAns() {
            return ans;
        }

        public Operand getOperand() {
            return operand;
        }
    }
}