package com.akieus.stst;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ConfigurationImpl implements Configuration {

    private final PriceSource[] sources;
    private final PriceProvider[] providers;

    public ConfigurationImpl(PriceSource[] sources, PriceProvider[] providers) {
        checkNotNull(sources);
        checkNotNull(providers);
        checkArgument(sources.length == providers.length);

        this.sources = sources;
        this.providers = providers;
    }

    public int getSize() {
        return sources.length;
    }

    public PriceSource getSource(int index) {
        assert index < sources.length;
        return sources[index];
    }

    public PriceProvider getProvider(int index) {
        assert index < providers.length;
        return providers[index];
    }
}

