package com.akieus.stst;

import javax.annotation.Nullable;

public interface Configuration {
    int getSize();

    PriceSource getSource(int index);

    @Nullable
    PriceProvider getProvider(int index);
}

