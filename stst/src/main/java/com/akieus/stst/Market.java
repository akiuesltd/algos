package com.akieus.stst;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A Market is a combintation of {@link PriceSource} and {@link PriceProvider}. PriceProvider may be null.
 * <p>
 * Each market has a unique Id that is used to quickly find the market in an array of markets.
 */
public class Market {
    public static final Market[] ALL_MARKETS;

    static {
        // totalMarkets = all combinations of source & providers including null provider
        int totalMarkets = PriceSource.values().length * (PriceProvider.values().length + 1);
        ALL_MARKETS = new Market[totalMarkets];
        for (PriceSource source : PriceSource.values()) {
            // Deal with NULL provider as a special case
            Market market = new Market(source, null);
            ALL_MARKETS[market.getId()] = market;

            for (PriceProvider provider : PriceProvider.values()) {
                market = new Market(source, provider);
                ALL_MARKETS[market.getId()] = market;
            }
        }
    }

    private final PriceSource source;
    private final PriceProvider provider;
    private final int id;

    public Market(PriceSource source, PriceProvider provider) {
        checkNotNull(source);
        this.source = source;
        this.provider = provider;
        id = calculateMarketId(source, provider);
    }

    public static int calculateMarketId(PriceSource source, PriceProvider provider) {
//        if (provider == null) {
//            return source.ordinal();
//        }
//        return provider.ordinal() << 6 | source.ordinal();
        return source.ordinal() + PriceSource.values().length * (provider == null ? 0 : provider.ordinal() + 1);
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
