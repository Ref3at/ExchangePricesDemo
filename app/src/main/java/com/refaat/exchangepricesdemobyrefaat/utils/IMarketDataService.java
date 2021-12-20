package com.refaat.exchangepricesdemobyrefaat.utils;

import com.refaat.exchangepricesdemobyrefaat.utils.graphData.PriceBar;
import com.refaat.exchangepricesdemobyrefaat.utils.graphData.PriceSeries;
import com.scichart.core.common.Action1;

public interface IMarketDataService {
    void subscribePriceUpdate(Action1<PriceBar> callback);

    void clearSubscriptions();

    PriceSeries getHistoricalData(int numberBars);

    void stopGenerator();

    void updateTheInterval(Integer integer);
}
