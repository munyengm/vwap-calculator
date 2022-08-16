package com.loudsight.vwap;

public class TestParam {
    private double[] inputPrices;
    private double[] inputVolumes;
    private Instrument[] inputInstruments;
    private Market[] inputMarkets;
    private State[] inputStates;
    private double[] expectedVwaps;
    private State[] expectedStates;
    private double[] expectedVolumes;

    public double[] getInputPrices() {
        return inputPrices;
    }

    public void setInputPrices(double... inputPrices) {
        this.inputPrices = inputPrices;
    }

    public double[] getInputVolumes() {
        return inputVolumes;
    }

    public void setInputVolumes(double... inputVolumes) {
        this.inputVolumes = inputVolumes;
    }

    public Instrument[] getInputInstruments() {
        return inputInstruments;
    }

    public void setInputInstruments(Instrument... inputInstruments) {
        this.inputInstruments = inputInstruments;
    }

    public Market[] getInputMarkets() {
        return inputMarkets;
    }

    public void setInputMarkets(Market... inputMarkets) {
        this.inputMarkets = inputMarkets;
    }

    public State[] getInputStates() {
        return inputStates;
    }

    public void setInputStates(State... inputStates) {
        this.inputStates = inputStates;
    }

    public double[] getExpectedVwaps() {
        return expectedVwaps;
    }

    public void setExpectedVwaps(double... expectedVwaps) {
        this.expectedVwaps = expectedVwaps;
    }

    public State[] getExpectedStates() {
        return expectedStates;
    }

    public void setExpectedStates(State... expectedStates) {
        this.expectedStates = expectedStates;
    }

    public double[] getExpectedVolumes() {
        return expectedVolumes;
    }

    public void setExpectedVolumes(double... expectedVolumes) {
        this.expectedVolumes = expectedVolumes;
    }
}
