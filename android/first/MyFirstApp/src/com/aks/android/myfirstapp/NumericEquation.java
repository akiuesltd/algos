package com.aks.android.myfirstapp;

public class NumericEquation {
    private String question;
    private int answer;

    public NumericEquation(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    public String question() {
        return question;
    }

    public int answer() {
        return answer;
    }

}
