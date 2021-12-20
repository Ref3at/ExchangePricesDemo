package com.refaat.exchangepricesdemobyrefaat.view.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.refaat.exchangepricesdemobyrefaat.R;
import com.refaat.exchangepricesdemobyrefaat.model.CurrencyPairItem;
import com.refaat.exchangepricesdemobyrefaat.databinding.FragmentCurrencyPairPriceGraphBinding;
import com.refaat.exchangepricesdemobyrefaat.utils.IMarketDataService;
import com.refaat.exchangepricesdemobyrefaat.utils.graphData.MarketDataService;
import com.refaat.exchangepricesdemobyrefaat.utils.graphData.MovingAverage;
import com.refaat.exchangepricesdemobyrefaat.utils.graphData.PriceBar;
import com.refaat.exchangepricesdemobyrefaat.utils.graphData.PriceSeries;
import com.refaat.exchangepricesdemobyrefaat.viewModel.MainActivityViewModel;
import com.scichart.charting.Direction2D;
import com.scichart.charting.model.dataSeries.IOhlcDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.annotations.AxisMarkerAnnotation;
import com.scichart.charting.visuals.axes.AutoRange;
import com.scichart.charting.visuals.axes.CategoryDateAxis;
import com.scichart.charting.visuals.axes.NumericAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.FastOhlcRenderableSeries;
import com.scichart.core.common.Action1;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.data.model.IRange;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


public class CurrencyPairPriceGraphFragment extends Fragment {

    FragmentCurrencyPairPriceGraphBinding binding;
    MainActivityViewModel mainActivityViewModel;
    private static final int SECONDS_IN_FIVE_MINUTES = 5 * 60;
    public static final int DEFAULT_POINT_COUNT = 50;
    public static final int SMA_SERIES_COLOR = 0xFFFFA500;
    public static final int STOKE_UP_COLOR = 0xFF00AA00;
    public static final int STROKE_DOWN_COLOR = 0xFFFF0000;
    public static final float STROKE_THICKNESS = 1.5f;
    protected final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();
    private final IOhlcDataSeries<Date, Double> ohlcDataSeries = sciChartBuilder.newOhlcDataSeries(Date.class, Double.class).withSeriesName("Price Series").build();
    private AxisMarkerAnnotation smaAxisMarker = sciChartBuilder.newAxisMarkerAnnotation().withY1(0d).withBackgroundColor(SMA_SERIES_COLOR).build();
    private AxisMarkerAnnotation ohlcAxisMarker = sciChartBuilder.newAxisMarkerAnnotation().withY1(0d).withBackgroundColor(STOKE_UP_COLOR).build();
    private IMarketDataService marketDataService;
    private final MovingAverage sma50 = new MovingAverage(50);
    private PriceBar lastPrice;

    public CurrencyPairPriceGraphFragment() {
        super(R.layout.fragment_currency_pair_price_graph);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCurrencyPairPriceGraphBinding.bind(view);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        int index = CurrencyPairPriceGraphFragmentArgs.fromBundle(getArguments()).getPairIndex();
        CurrencyPairItem currencyPairItem = mainActivityViewModel.getCurrencyPairListMutableLiveData().getValue().get(index);

        binding.txtPairNameTimeFrame.setText(currencyPairItem.getName() + ", " + mainActivityViewModel.getTheSelectedInterval().getValue() + " Sec");

        // Market data service simulates live ticks. We want to load the chart with 150 historical bars then later do real-time ticking as new data comes in
        Date date = Calendar.getInstance().getTime();
        this.marketDataService = new MarketDataService(date, 1, mainActivityViewModel.getTheSelectedIntervalInMillisecond());
        initializeMainChart(binding.surface);

        UpdateSuspender.using(binding.surface, new Runnable() {
            @Override
            public void run() {
                int count = DEFAULT_POINT_COUNT;
                if (savedInstanceState != null) {
                    count = savedInstanceState.getInt("count");
                    double rangeMin = savedInstanceState.getDouble("rangeMin");
                    double rangeMax = savedInstanceState.getDouble("rangeMax");
                    binding.surface.getXAxes().get(0).getVisibleRange().setMinMaxDouble(rangeMin, rangeMax);
                }
                PriceSeries prices = marketDataService.getHistoricalData(count);
                ohlcDataSeries.append(prices.getDateData(), prices.getOpenData(), prices.getHighData(), prices.getLowData(), prices.getCloseData());
                marketDataService.subscribePriceUpdate(onNewPrice());
            }
        });

    }

    private void initializeMainChart(final SciChartSurface surface) {
        final CategoryDateAxis xAxis = sciChartBuilder.newCategoryDateAxis()
                .withBarTimeFrame(SECONDS_IN_FIVE_MINUTES)
                .withDrawMinorGridLines(false)
                .withGrowBy(0, 0.1)
                .build();
        final NumericAxis yAxis = sciChartBuilder.newNumericAxis().withAutoRangeMode(AutoRange.Always).build();

        final FastOhlcRenderableSeries ohlc = sciChartBuilder.newOhlcSeries()
                .withStrokeUp(STOKE_UP_COLOR, STROKE_THICKNESS)
                .withStrokeDown(STROKE_DOWN_COLOR, STROKE_THICKNESS)
                .withStrokeStyle(STOKE_UP_COLOR)
                .withDataSeries(ohlcDataSeries)
                .build();
        final FastLineRenderableSeries line = sciChartBuilder.newLineSeries().withStrokeStyle(SMA_SERIES_COLOR, STROKE_THICKNESS).build();
//        final FastLineRenderableSeries line = sciChartBuilder.newLineSeries().withStrokeStyle(SMA_SERIES_COLOR, STROKE_THICKNESS).withDataSeries(xyDataSeries).build();

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public synchronized void run() {
                Collections.addAll(surface.getXAxes(), xAxis);
                Collections.addAll(surface.getYAxes(), yAxis);
                Collections.addAll(surface.getRenderableSeries(), ohlc, line);
                Collections.addAll(surface.getAnnotations(), smaAxisMarker, ohlcAxisMarker);
                Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroup()
                        .withXAxisDragModifier().build()
                        .withZoomPanModifier().withReceiveHandledEvents(true).withXyDirection(Direction2D.XDirection).build()
                        .withZoomExtentsModifier().build()

//                        .withLegendModifier().withOrientation(Orientation.HORIZONTAL)
//                        .withPosition(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 20).
//                                withReceiveHandledEvents(true).build()
                        .build());
            }
        });
    }

    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("count", ohlcDataSeries.getCount());
        IRange range = binding.surface.getXAxes().get(0).getVisibleRange();
        savedInstanceState.putDouble("rangeMin", range.getMinAsDouble());
        savedInstanceState.putDouble("rangeMax", range.getMaxAsDouble());
    }

    @NonNull
    private synchronized Action1<PriceBar> onNewPrice() {
        return new Action1<PriceBar>() {
            @Override
            public void execute(final PriceBar price) {
                // Update the last price, or append?
                double smaLastValue;

                if (lastPrice != null && lastPrice.getDate() == price.getDate()) {
                    ohlcDataSeries.update(ohlcDataSeries.getCount() - 1, price.getOpen(), price.getHigh(), price.getLow(), price.getClose());

                    smaLastValue = sma50.update(price.getClose()).getCurrent();
//                    xyDataSeries.updateYAt(xyDataSeries.getCount() - 1, smaLastValue);

                } else {
                    ohlcDataSeries.append(price.getDate(), price.getOpen(), price.getHigh(), price.getLow(), price.getClose());

                    smaLastValue = sma50.push(price.getClose()).getCurrent();
//                    xyDataSeries.append(price.getDate(), smaLastValue);


                    // If the latest appending point is inside the viewport (i.e. not off the edge of the screen)
                    // then scroll the viewport 1 bar, to keep the latest bar at the same place
                    final IRange visibleRange = binding.surface.getXAxes().get(0).getVisibleRange();
                    if (visibleRange.getMaxAsDouble() > ohlcDataSeries.getCount()) {
                        visibleRange.setMinMaxDouble(visibleRange.getMinAsDouble() + 1, visibleRange.getMaxAsDouble() + 1);
                    }
                }

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ohlcAxisMarker.setBackgroundColor(price.getClose() >= price.getOpen() ? STOKE_UP_COLOR : STROKE_DOWN_COLOR);
                    }
                });

                smaAxisMarker.setY1(smaLastValue);
                ohlcAxisMarker.setY1(price.getClose());

                lastPrice = price;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                // navigate to settings screen
                showSettingDialog();
                return true;
            }
            case R.id.action_about: {
                // save about screen
                NavDirections navDirections = CurrencyPairPriceGraphFragmentDirections.actionCurrencyPairPriceGraphFragmentToAboutFragment();
                Navigation.findNavController(getView()).navigate(navDirections);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        marketDataService.stopGenerator();
        binding = null;
    }

    void showSettingDialog() {
        new SettingDialog().show(getParentFragmentManager(), "SettingDialog");
    }


}