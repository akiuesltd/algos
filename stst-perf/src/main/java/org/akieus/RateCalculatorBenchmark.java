/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.akieus;

import com.akieus.stst.*;
import org.openjdk.jmh.annotations.*;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RateCalculatorBenchmark {

    private static final int EXECUTIONS = 1000;

    private static final Market TEST_MARKET_1 = new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_2 = new Market(PriceSource.SOURCE1, PriceProvider.PROVIDER2);
    private static final Market TEST_MARKET_3 = new Market(PriceSource.SOURCE2, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_4 = new Market(PriceSource.SOURCE3, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_5 = new Market(PriceSource.SOURCE4, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_6 = new Market(PriceSource.SOURCE5, PriceProvider.NULL_PROVIDER);
    private static final Market TEST_MARKET_7 = new Market(PriceSource.SOURCE6, PriceProvider.PROVIDER1);
    private static final Market TEST_MARKET_8 = new Market(PriceSource.SOURCE7, PriceProvider.PROVIDER2);
    private static final Market TEST_MARKET_9 = new Market(PriceSource.SOURCE8, PriceProvider.PROVIDER3);
    private static final Market TEST_MARKET_10 = new Market(PriceSource.SOURCE9, PriceProvider.PROVIDER4);
    private static final Market TEST_MARKET_11 = new Market(PriceSource.SOURCE10, PriceProvider.PROVIDER5);
    private static final Market TEST_MARKET_12 = new Market(PriceSource.SOURCE11, PriceProvider.PROVIDER6);
    private static final Market TEST_MARKET_13 = new Market(PriceSource.SOURCE12, PriceProvider.PROVIDER7);
    private static final Market TEST_MARKET_14 = new Market(PriceSource.SOURCE13, PriceProvider.PROVIDER8);
    private static final Market TEST_MARKET_15 = new Market(PriceSource.SOURCE14, PriceProvider.PROVIDER9);
    private static final Market[] CONFIGURED_MARKETS = new Market[]{
            TEST_MARKET_1, TEST_MARKET_2, TEST_MARKET_3, TEST_MARKET_4, TEST_MARKET_5,
            TEST_MARKET_6, TEST_MARKET_7, TEST_MARKET_8, TEST_MARKET_9, TEST_MARKET_10,
            TEST_MARKET_11, TEST_MARKET_12, TEST_MARKET_13, TEST_MARKET_14, TEST_MARKET_15
    };

    private static TestFxPrice[] PRICES = {};

    static {
        prepPrices();
    }

    private static void prepPrices() {
        PRICES = new TestFxPrice[EXECUTIONS];
        for (int i = 0; i < EXECUTIONS; i++) {
            PRICES[i] = new TestFxPrice();
            PRICES[i].bid = nonZeroRandomDouble();
            PRICES[i].offer = nonZeroRandomDouble();
            PRICES[i].stale = ThreadLocalRandom.current().nextBoolean();
            Market market = CONFIGURED_MARKETS[ThreadLocalRandom.current().nextInt(CONFIGURED_MARKETS.length)];
            PRICES[i].provider = market.getProvider();
            PRICES[i].source = market.getSource();
        }
    }

    private static double nonZeroRandomDouble() {
        while (true) {
            double random = ThreadLocalRandom.current().nextDouble();
            if (random != 0.0) {
                return random;
            }
        }
    }

    public static void main(String... args) {
        RateCalculatorBenchmark benchmark = new RateCalculatorBenchmark();

        ThreadState threadState = new ThreadState();
        threadState.setup();
        for (int i = 0; i < EXECUTIONS; i++) {
            benchmark.singleExecution(threadState);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void singleExecution(ThreadState threadState) {
        threadState.calculator.onFxPrice(PRICES[threadState.counter]);
        threadState.calculator.calculate();
        threadState.counter++;
        if (threadState.counter == EXECUTIONS) {
            threadState.counter = 0;
        }
    }

    @State(Scope.Thread)
    public static class ThreadState {
        private ReferenceRateCalculator calculator = null;
        private int counter;

        @Setup
        public void setup() {
            calculator = new ReferenceRateCalculatorImpl();
            calculator.onConfiguration(new ConfigurationImpl(CONFIGURED_MARKETS));
        }

    }

    private static class TestFxPrice implements FxPrice {
        private double bid;
        private double offer;
        private boolean stale;
        private PriceSource source;
        private PriceProvider provider;

        @Override
        public double getBid() {
            return bid;
        }

        @Override
        public double getOffer() {
            return offer;
        }

        @Override
        public boolean isStale() {
            return stale;
        }

        @Override
        public PriceSource getSource() {
            return source;
        }

        @Nullable
        @Override
        public PriceProvider getProvider() {
            return provider;
        }
    }
}
