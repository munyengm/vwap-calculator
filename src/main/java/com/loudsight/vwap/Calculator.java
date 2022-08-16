package com.loudsight.vwap;

/**
 * A VWAP calculator abstraction. Implementations of this interface must conform to the following requirements,
 * The method applyMarketUpdate must return the two-way VWAP for the instrument specified in the MarketUpdate parameter.
 * Each instrument can receive a two-way price update from one of 50 markets, and in calculating the VWAP for the instrument
 *  the calculator considers the most recent price update for each market (received so far)
 */
public interface Calculator {
    TwoWayPrice applyMarketUpdate(final MarketUpdate twoWayMarketPrice);
}
