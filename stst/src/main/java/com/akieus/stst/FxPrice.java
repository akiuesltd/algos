package com.akieus.stst;

import javax.annotation.Nullable;

public interface FxPrice {
    double getBid();

    double getOffer();

    boolean isStale();

    PriceSource getSource();

    @Nullable
    PriceProvider getProvider();

}
