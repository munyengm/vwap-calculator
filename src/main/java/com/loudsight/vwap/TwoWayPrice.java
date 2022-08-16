package com.loudsight.vwap;

/**
 * Two-way price for an instrument
 */
public interface TwoWayPrice {
    Instrument getInstrument();

    State getState();

    double getBidPrice();

    double getOfferAmount();

    double getOfferPrice();

    double getBidAmount();
}
