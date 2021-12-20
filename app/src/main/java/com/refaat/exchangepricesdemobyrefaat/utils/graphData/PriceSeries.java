package com.refaat.exchangepricesdemobyrefaat.utils.graphData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceSeries extends ArrayList<PriceBar> {

    public PriceSeries() {
    }

    public PriceSeries(int capacity) {
        super(capacity);
    }

    public List<Date> getDateData() {
        final List<Date> result = new ArrayList<>();
        for (PriceBar priceBar : this) {
            result.add(priceBar.getDate());
        }
        return result;
    }

    public List<Double> getLowData() {
        final List<Double> result = new ArrayList<>();
        for (PriceBar priceBar : this) {
            result.add(priceBar.getLow());
        }
        return result;
    }

    public List<Double> getCloseData() {
        final List<Double> result = new ArrayList<>();
        for (PriceBar priceBar : this) {
            result.add(priceBar.getClose());
        }
        return result;
    }

    public List<Double> getOpenData() {
        final List<Double> result = new ArrayList<>();
        for (PriceBar priceBar : this) {
            result.add(priceBar.getOpen());
        }
        return result;
    }

    public List<Double> getHighData() {
        final List<Double> result = new ArrayList<>();
        for (PriceBar priceBar : this) {
            result.add(priceBar.getHigh());
        }
        return result;
    }

    public List<Long> getVolumeData() {
        final List<Long> result = new ArrayList<>();
        for (PriceBar priceBar : this) {
            result.add(priceBar.getVolume());
        }
        return result;
    }

    public List<Double> getIndexesAsDouble() {
        final List<Double> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            result.add((double) i);
        }
        return result;
    }

    public List<Integer> getIndexes() {
        final List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            result.add(i);
        }
        return result;
    }
}