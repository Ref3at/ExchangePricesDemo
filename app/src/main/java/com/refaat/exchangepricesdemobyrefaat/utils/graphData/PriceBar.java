package com.refaat.exchangepricesdemobyrefaat.utils.graphData;


import java.util.Date;

public class PriceBar {

    private Date date = new Date();
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final long volume;

    public PriceBar(double open, double high, double low, double close, long volume) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public PriceBar(Date date, double open, double high, double low, double close, long volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolume() {
        return volume;
    }
}
