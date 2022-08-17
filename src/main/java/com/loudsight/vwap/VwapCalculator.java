package com.loudsight.vwap;

import com.loudsight.vwap.utils.LoggingHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An implementation of com.loudsight.vwap.Calculator. The following important points should be noted:
 * - For any given market, only the most recent price is considered valid and only the most recent price is included
 *  in the VWAP calculation.
 * - Calculator.applyMarketUpdate is assumed to be called from a single thread.
 * - If any MarketUpdate used in deriving the VWAP is indicative, the calculated TwoWayPrice is also marked as
 *  indicative, otherwise it is firm.
 *
 * VWAP Calculation
 *  The VWAP two-way price for an instrument is defined as:
 *  Bid = Sum(Market Bid Price * Market Bid Amount)/ Sum(Market Bid Amount)
 *  Offer = Sum(Market Offer Price * Market Offer Amount)/ Sum(Market Offer Amount)
 */
public class VwapCalculator implements Calculator {
    private static final LoggingHelper LOG = LoggingHelper.wrap(VwapCalculator.class);

    private final Map<Instrument, VwapPrice> priceMap = new HashMap<>();

    public VwapCalculator() {
        // Assumption is that an instance of this class is created once in a single thread.
        // Initialise all the instruments to avoid allocations during updates.
        Stream.of(Instrument.values()).forEach(instrument -> priceMap.put(instrument, new VwapPrice(instrument)));
    }

    @Override
    public TwoWayPrice applyMarketUpdate(MarketUpdate twoWayMarketPrice) {
        var vwapPrice = priceMap.get(twoWayMarketPrice.getTwoWayPrice().getInstrument());

        if (vwapPrice == null) {
            LOG.logError("Ignoring market update for an unknown instrument");

            return null;
        }

        return vwapPrice.recalculateVwap(twoWayMarketPrice);
    }
}