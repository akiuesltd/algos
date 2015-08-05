package com.aks.android.myfirstapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

public class QuestionBank {

    private static final String DATA_FILE = "questions.data";
    private static final String DELIMITER = "::";

    private final List<NumericEquation> equations = new ArrayList<NumericEquation>();

    private final Context context;

    public QuestionBank(final Context context) {
        this.context = context;
    }

    public void load() throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream is = assetManager.open(DATA_FILE);
        List<String> data = IOUtils.readFile(is);
        equations.clear();
        for (String line : data) {
            String[] tokens = line.split(DELIMITER);
            if (tokens.length != 2) {
                throw new IllegalStateException("Corrupt data");
            }
            try {
                equations.add(new NumericEquation(tokens[0].trim(), Integer.parseInt(tokens[1].trim())));
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Corrupt data");
            }
        }
    }

    public NumericEquation entry(int i) {
        if (i >= size()) {
            throw new IllegalArgumentException("Not enough questions");
        }
        return equations.get(i);
    }
    
    public int size() {
        return equations.size();
    }
}
