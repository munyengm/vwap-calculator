package com.loudsight.vwap;

import java.util.HashMap;
import java.util.Map;

/**
 * Vwap price holder
 */
public class VwapPrice implements TwoWayPrice {
    Map<Market, TwoWayPrice> marketPriceMap = new HashMap<>();

    private final Instrument instrument;
    int indicativeMarkets = 0;
    double bidPriceMarketProductSum;
    double bidAmountSum;
    double offerPriceMarketProductSum;
    double offerAmountSum;

    public VwapPrice(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public Instrument getInstrument() {
        return instrument;
    }

    @Override
    public State getState() {
        return indicativeMarkets == 0? State.FIRM : State.INDICATIVE;
    }

    @Override
    public double getBidPrice() {
        return bidPriceMarketProductSum / bidAmountSum;
    }

    @Override
    public double getBidAmount() {
        return bidAmountSum;
    }

    @Override
    public double getOfferPrice() {
        return offerPriceMarketProductSum / offerAmountSum;
    }

    @Override
    public double getOfferAmount() {
        return offerAmountSum;
    }

    public TwoWayPrice recalculateVwap(MarketUpdate marketUpdate) {
        var newTwoWayPrice = marketUpdate.getTwoWayPrice();

        if (newTwoWayPrice.getState() == State.INDICATIVE) {
            ++indicativeMarkets;
        }
        bidAmountSum += newTwoWayPrice.getBidAmount();
        bidPriceMarketProductSum += (newTwoWayPrice.getBidPrice() * newTwoWayPrice.getBidAmount());

        offerAmountSum += newTwoWayPrice.getOfferAmount();
        offerPriceMarketProductSum += (newTwoWayPrice.getOfferPrice() * newTwoWayPrice.getOfferAmount());

        var existingPrice = marketPriceMap.put(marketUpdate.getMarket(), newTwoWayPrice);

        if (existingPrice != null) {
            bidAmountSum -= existingPrice.getBidAmount();
            bidPriceMarketProductSum -= (existingPrice.getBidPrice() * existingPrice.getBidAmount());

            offerAmountSum -= existingPrice.getOfferAmount();
            offerPriceMarketProductSum -= (existingPrice.getOfferPrice() * existingPrice.getOfferAmount());


            if (existingPrice.getState() == State.INDICATIVE) {
                --indicativeMarkets;
            }
        }

        return this;
    }
}
