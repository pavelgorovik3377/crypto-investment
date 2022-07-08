package com.gorovik.crypto.model;

public class CurrencyStatistics {

    double oldest;
    double newest;
    double minimum;
    double maximum;

    public CurrencyStatistics(double oldest, double newest, double minimum, double maximum) {
        this.oldest = oldest;
        this.newest = newest;
        this.minimum = minimum;
        this.maximum = maximum;
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
