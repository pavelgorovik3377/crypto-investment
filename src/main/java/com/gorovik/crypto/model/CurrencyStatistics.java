package com.gorovik.crypto.model;

/**
 * Stores information about the oldest, newest, minimal and maximal rate of a crypto currency.
 */
public class CurrencyStatistics {

    private String currencyCode;

    private double oldest;

    private double newest;

    private double minimum;

    private double maximum;

    public CurrencyStatistics(String currencyCode, double oldest, double newest, double minimum, double maximum) {
        this.currencyCode = currencyCode;
        this.oldest = oldest;
        this.newest = newest;
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getOldest() {
        return oldest;
    }

    public void setOldest(double oldest) {
        this.oldest = oldest;
    }

    public double getNewest() {
        return newest;
    }

    public void setNewest(double newest) {
        this.newest = newest;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }
}
