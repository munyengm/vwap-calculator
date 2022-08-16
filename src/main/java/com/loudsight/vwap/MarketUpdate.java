package com.loudsight.vwap;

/**
 * Market update specifying the two-way price and market
 */
public interface MarketUpdate {
    Market getMarket();

    TwoWayPrice getTwoWayPrice();
}
