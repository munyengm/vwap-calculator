package com.loudsight.vwap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.loudsight.vwap.Instrument.INSTRUMENT0;
import static com.loudsight.vwap.Market.MARKET0;
import static com.loudsight.vwap.Market.MARKET1;
import static com.loudsight.vwap.State.FIRM;
import static com.loudsight.vwap.State.INDICATIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VwapCalculatorTest {

    private VwapCalculator vwapCalculator;

    @BeforeEach
    public void setUp() {
        vwapCalculator = new VwapCalculator();
    }

    @Test
    public void singleFirmPrice() {
        var param = new TestParam();
        param.setInputPrices(10.48);
        param.setInputVolumes(15_687);
        param.setInputInstruments(INSTRUMENT0);
        param.setInputMarkets(MARKET0);
        param.setInputStates(FIRM);
        param.setExpectedVwaps((10.48 * 15_687) / 15_687); // = 10.48
        param.setExpectedStates(FIRM);
        param.setExpectedVolumes(15_687);

        verifyVwapCalculations(param);
    }

    @Test
    public void singleIndicativePrice() {
        var param = new TestParam();
        param.setInputPrices(10.48);
        param.setInputVolumes(15_687);
        param.setInputInstruments(INSTRUMENT0);
        param.setInputMarkets(MARKET0);
        param.setInputStates(INDICATIVE);
        param.setExpectedVwaps((10.48 * 15_687) / 15_687); // = 10.48
        param.setExpectedStates(INDICATIVE);
        param.setExpectedVolumes(15_687);

        verifyVwapCalculations(param);
    }

    @Test
    public void multipleFirmPrices() {
        var param = new TestParam();
        param.setInputPrices(10.48, 9.42);
        param.setInputVolumes(15_687, 22_374);
        param.setInputInstruments(INSTRUMENT0, INSTRUMENT0);
        param.setInputMarkets(MARKET0, MARKET1);
        param.setInputStates(FIRM, FIRM);
        param.setExpectedVwaps(
                10.48 * 15_687 / 15_687, // = 10.48
                (10.48 * 15_687 + 9.42 * 22_374) / 38_061 // = 5.54
        );
        param.setExpectedStates(FIRM, FIRM);
        param.setExpectedVolumes(15_687, 15_687 + 22_374);

        verifyVwapCalculations(param);
    }

    @Test
    public void multipleMixedPrices() {
        var param = new TestParam();
        param.setInputPrices(10.48, 9.42);
        param.setInputVolumes(15_687, 22_374);
        param.setInputInstruments(INSTRUMENT0, INSTRUMENT0);
        param.setInputMarkets(MARKET0, MARKET1);
        param.setInputStates(FIRM, INDICATIVE);
        param.setExpectedVwaps(
                10.48 * 15_687 / 15_687, // = 10.48
                (10.48 * 15_687 + 9.42 * 22_374) / 38_061 // = 5.54
        );
        param.setExpectedStates(FIRM, INDICATIVE);
        param.setExpectedVolumes(15_687, 38_061);

        verifyVwapCalculations(param);
    }

    @Test
    public void singleMarketWithMultiplePricesEndingWithIndicative() {
        var param = new TestParam();
        param.setInputPrices(10.48, 9.42);
        param.setInputVolumes(15_687, 22_374);
        param.setInputInstruments(INSTRUMENT0, INSTRUMENT0);
        param.setInputMarkets(MARKET0, MARKET0);
        param.setInputStates(FIRM, INDICATIVE);
        param.setExpectedVwaps(
                10.48 * 15_687 / 15_687, // = 10.48
                (9.42 * 22_374) / 22_374 // = 5.54
        );
        param.setExpectedStates(FIRM, INDICATIVE);
        param.setExpectedVolumes(15_687, 22_374);

        verifyVwapCalculations(param);
    }

    @Test
    public void singleMarketWithMultiplePricesEndingWithFirm() {
        var param = new TestParam();
        param.setInputPrices(10.48, 9.42, 11.48);
        param.setInputVolumes(15_687, 22_374, 12_742);
        param.setInputInstruments(INSTRUMENT0, INSTRUMENT0, INSTRUMENT0);
        param.setInputMarkets(MARKET0, MARKET0, MARKET0);
        param.setInputStates(FIRM, INDICATIVE, FIRM);
        param.setExpectedVwaps(
                10.48 * 15_687 / 15_687, // = 10.48
                9.42 * 22_374 / 22_374, // = 9.42
                11.48 * 12_742 / 12_742 // = 11.48
        );
        param.setExpectedStates(FIRM, INDICATIVE, FIRM);
        param.setExpectedVolumes(15_687, 22_374, 12_742);

        verifyVwapCalculations(param);
    }

    private void verifyVwapCalculations(TestParam param) {
        var length = param.getInputInstruments().length;

        for (int i = 0; i < length; i++) {
            var price = new SimplePrice(
                    param.getInputInstruments()[i],
                    param.getInputStates()[i],
                    param.getInputPrices()[i],
                    param.getInputVolumes()[i],
                    param.getInputPrices()[i],
                    param.getInputVolumes()[i]);
            var marketUpdate = new SimpleMarketUpdate(param.getInputMarkets()[i], price);
            var vwap = vwapCalculator.applyMarketUpdate(marketUpdate);
            assertEquals(param.getExpectedVwaps()[i], vwap.getOfferPrice(), 0.000001);
            assertEquals(param.getExpectedVwaps()[i], vwap.getBidPrice(), 0.000001);
            assertEquals(param.getExpectedVolumes()[i], vwap.getBidAmount(), 0.000001);
            assertEquals(param.getExpectedVolumes()[i], vwap.getBidAmount(), 0.000001);
            assertEquals(param.getExpectedStates()[i], vwap.getState());
        }
    }
}
