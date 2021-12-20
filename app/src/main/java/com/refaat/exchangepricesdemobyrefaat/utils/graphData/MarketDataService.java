package com.refaat.exchangepricesdemobyrefaat.utils.graphData;

import com.refaat.exchangepricesdemobyrefaat.utils.IMarketDataService;
import com.scichart.core.common.Action1;

import java.util.Date;

public class MarketDataService implements IMarketDataService {

    public interface INewDataObserver {
        void onNewData(PriceBar data);
    }

    public interface IUpdateDataObserver {
        void onUpdateData(PriceBar data);
    }

    private final RandomPricesDataSource generator;

    public MarketDataService(Date startDate, int timeFrameMinutes, int tickTimerIntervals) {
        this.generator = new RandomPricesDataSource(timeFrameMinutes, true, tickTimerIntervals, 25, 367367, 30, startDate);
    }

    @Override
    public void subscribePriceUpdate(final Action1<PriceBar> callback) {
        if (!generator.isRunning()) {
            generator.newDataObserver = new INewDataObserver() {
                @Override
                public void onNewData(PriceBar data) {
                    callback.execute(data);
                }
            };
            generator.updateDataObserver = new IUpdateDataObserver() {
                @Override
                public void onUpdateData(PriceBar data) {
                    callback.execute(data);
                }
            };

            generator.startGeneratePriceBars();
        }
    }

    @Override
    public void clearSubscriptions() {
        if (generator.isRunning()) {
            generator.stopGeneratePriceBars();
            generator.clearObservers();
        }
    }

    @Override
    public PriceSeries getHistoricalData(int numberBars) {
        PriceSeries prices = new PriceSeries(numberBars);
        for (int i = 0; i < numberBars; i++) {
            prices.add(generator.getNextData());
        }

        return prices;
    }

    public void stopGenerator() {
        clearSubscriptions();
        generator.cancelScheduler();
    }

    @Override
    public void updateTheInterval(Integer integer) {
        generator.updateTickTimerIntervals(integer);
    }
}
