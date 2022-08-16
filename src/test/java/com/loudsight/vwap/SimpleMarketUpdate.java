package com.loudsight.vwap;

public class SimpleMarketUpdate implements MarketUpdate {

    private final Market market;
    private final TwoWayPrice twoWayPrice;

    public SimpleMarketUpdate(Market market, TwoWayPrice twoWayPrice) {

        this.market = market;
        this.twoWayPrice = twoWayPrice;
    }

    @Override
    public Market getMarket() {
        return market;
    }

    @Override
    public TwoWayPrice getTwoWayPrice() {
        return twoWayPrice;
    }
}
