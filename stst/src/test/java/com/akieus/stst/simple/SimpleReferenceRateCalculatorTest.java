package com.akieus.stst.simple;

import com.akieus.stst.ReferenceRateCalculator;
import com.akieus.stst.ReferenceRateCalculatorTest;
import com.akieus.stst.lowgc.LowGCReferenceRateCalculator;

public class SimpleReferenceRateCalculatorTest extends ReferenceRateCalculatorTest {

    @Override
    protected ReferenceRateCalculator newCalculator() {
        return new SimpleReferenceRateCalculator();
    }
}
