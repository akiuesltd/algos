package com.aks.android.myfirstapp;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.android.myfirstapp.MESSAGE";
    public final static String EXTRA_BUTTON = "com.example.android.myfirstapp.BUTTON";
    public final static String STATE_QID = "com.example.android.myfirstapp.qid";

    private QuestionBank qbank;
    private int qId;

    public MainActivity() {
    }

    private void loadQuestionBank() {
        if (qbank == null) {
            qbank = new QuestionBank(this);
        }
        try {
            qbank.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionView().setTextSize((int) (questionView().getTextSize() * 1.5));

        loadQuestionBank();
        if (savedInstanceState == null) {
            resetQuestion();
        }
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        qId = savedInstanceState.getInt(STATE_QID);
        resetQuestion();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_QID, qId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void checkAnswer(View view) {
        Intent intent = new Intent(this, DisplayResultActivity.class);
        EditText editText = (EditText) findViewById(R.id.answer);
        String answer = editText.getText().toString();
        boolean isCorrect = isCorrect(qId, answer);
        String result = isCorrect ? getResources().getString(R.string.correct_answer) : getResources().getString(
                R.string.wrong_answer);
        String button = isCorrect ? getResources().getString(R.string.next) : getResources().getString(R.string.back);
        intent.putExtra(EXTRA_MESSAGE, result);
        intent.putExtra(EXTRA_BUTTON, button);
        startActivityForResult(intent, 0);

        if (isCorrect) {
            qId = ++qId % qbank.size();
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resetAnswer();
        resetQuestion();
    }

    private boolean isCorrect(int qId, String answer) {
        try {
            return answer != null && answer.trim().length() != 0
                    && Integer.parseInt(answer) == qbank.entry(qId).answer();
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void resetQuestion() {
        questionView().setText(qbank.entry(qId).question());
    }

    private TextView questionView() {
        return (TextView) findViewById(R.id.question);
    }
    
    private void resetAnswer() {
        ((EditText) findViewById(R.id.answer)).setText("");
    }
}
