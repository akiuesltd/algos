package com.akieus.stst;

import javax.annotation.Nullable;

/**
 * @author aks
 * @since 30/10/15
 */
public interface Configuration {
    int getSize();

    PriceSource getSource(int index);

    @Nullable
    PriceProvider getProvider(int index);
}

