package com.akieus.stst;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ConfigurationImpl implements Configuration {

    private final Market[] markets;

    public ConfigurationImpl(final Market[] markets) {
        checkNotNull(markets);
        checkArgument(markets.length != 0);
        this.markets = markets;
    }

    public int getSize() {
        return markets.length;
    }

    public PriceSource getSource(final int index) {
        assert index < markets.length;
        return markets[index].getSource();
    }

    public PriceProvider getProvider(final int index) {
        assert index < markets.length;
        return markets[index].getProvider();
    }
}

