package com.akieus.stst;


import static com.google.common.base.Preconditions.checkNotNull;

public class Market {
    public static final Market[] ALL_MARKETS;

    static {
        int totalMarkets = PriceSource.values().length * PriceProvider.values().length;
        ALL_MARKETS = new Market[totalMarkets];
        for (PriceSource source : PriceSource.values()) {
            for (PriceProvider provider : PriceProvider.values()) {
                Market market = new Market(source, provider);
                ALL_MARKETS[market.getId()] = market;
            }
        }
    }

    private final PriceSource source;
    private final PriceProvider provider;
    private final int id;

    public Market(final PriceSource source, final PriceProvider provider) {
        checkNotNull(source);
        this.source = source;
        this.provider = provider == null ? PriceProvider.NULL_PROVIDER : provider;
        id = calculateMarketId(this.source, this.provider);
    }

    public static int calculateMarketId(final PriceSource source, final PriceProvider provider) {
        assert source != null;
        assert provider != null;
        return source.ordinal() + provider.ordinal() * PriceSource.values().length;
    }

    public PriceSource getSource() {
        return source;
    }

    public PriceProvider getProvider() {
        return provider;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: " + id
                + ", source: " + source.name()
                + ", provider: " + (provider == null ? "NULL" : provider.name());
    }
}
