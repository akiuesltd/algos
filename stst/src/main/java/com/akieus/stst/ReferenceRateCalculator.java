package com.akieus.stst;

public interface ReferenceRateCalculator {
    void onConfiguration(Configuration configuration);

    void onFxPrice(FxPrice fxPrice);

    FxPrice calculate();
}