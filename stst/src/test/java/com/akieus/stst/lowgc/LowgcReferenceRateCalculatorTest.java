package com.akieus.stst.lowgc;

import com.akieus.stst.ReferenceRateCalculatorTest;

public class LowgcReferenceRateCalculatorTest extends ReferenceRateCalculatorTest {

    @Override
    protected LowGCReferenceRateCalculator newCalculator() {
        return new LowGCReferenceRateCalculator();
    }
}
