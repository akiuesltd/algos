package com.akieus.stst.simple;

import com.akieus.stst.PriceProvider;
import com.akieus.stst.PriceSource;
import org.apache.commons.lang.ObjectUtils;

import static com.google.common.base.Preconditions.checkNotNull;

public class Market {
    private final PriceSource source;
    private final PriceProvider provider;

    public Market(PriceSource source, PriceProvider provider) {
        checkNotNull(source);
        this.source = source;
        this.provider = provider;
    }

    public PriceSource getSource() {
        return source;
    }

    public PriceProvider getProvider() {
        return provider;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof Market)) {
            return false;
        }
        Market that = (Market) o;

        return ObjectUtils.equals(this.source, that.source)
                && ObjectUtils.equals(this.provider, that.provider);
    }

    public int hashCode() {
        return source.ordinal() * 31 + (provider == null ? 0 : provider.ordinal());
    }
}




