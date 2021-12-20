package com.refaat.exchangepricesdemobyrefaat.viewModel;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.refaat.exchangepricesdemobyrefaat.model.CurrencyPairItem;
import com.refaat.exchangepricesdemobyrefaat.utils.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private boolean isSimulationStarted;

    private MutableLiveData<Integer> theSelectedInterval = new MutableLiveData<>();

    private final MutableLiveData<List<CurrencyPairItem>> currencyPairListMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<CurrencyPairItem>> getCurrencyPairListMutableLiveData() {
        return currencyPairListMutableLiveData;
    }


    public void startSimulation() {

        if (isSimulationStarted) {
            return;
        }
        isSimulationStarted = true;

        List<CurrencyPairItem> currencyPairItemList = new ArrayList<>();
        CurrencyPairItem currencyPairItem = new CurrencyPairItem("USD/EUR", 1.16258, 1.16298);
        CurrencyPairItem currencyPairItem1 = new CurrencyPairItem("EGP/AED", 1.87658, 1.65658);
        CurrencyPairItem currencyPairItem2 = new CurrencyPairItem("CAD/YEN", 4.55445, 4.54445);

        CurrencyPairItem currencyPairItem3 = new CurrencyPairItem("XAG/EUR", 1.16258, 1.16298);
        CurrencyPairItem currencyPairItem4 = new CurrencyPairItem("JPY/AED", 1.87658, 1.65658);
        CurrencyPairItem currencyPairItem5 = new CurrencyPairItem("CAD/AED", 4.55445, 4.54445);

        CurrencyPairItem currencyPairItem6 = new CurrencyPairItem("XAG/EUR", 1.16258, 1.16298);
        CurrencyPairItem currencyPairItem7 = new CurrencyPairItem("JPY/AED", 1.87658, 1.65658);
        CurrencyPairItem currencyPairItem8 = new CurrencyPairItem("CAD/AED", 4.55445, 4.54445);

        CurrencyPairItem currencyPairItem9 = new CurrencyPairItem("XAG/EUR", 1.16258, 1.16298);
        CurrencyPairItem currencyPairItem10 = new CurrencyPairItem("JPY/AED", 1.87658, 1.65658);
        CurrencyPairItem currencyPairItem11 = new CurrencyPairItem("CAD/AED", 4.55445, 4.54445);

        currencyPairItemList.add(currencyPairItem);
        currencyPairItemList.add(currencyPairItem1);
        currencyPairItemList.add(currencyPairItem2);
        currencyPairItemList.add(currencyPairItem3);
        currencyPairItemList.add(currencyPairItem4);
        currencyPairItemList.add(currencyPairItem5);
        currencyPairItemList.add(currencyPairItem6);
        currencyPairItemList.add(currencyPairItem7);
        currencyPairItemList.add(currencyPairItem8);
        currencyPairItemList.add(currencyPairItem9);
        currencyPairItemList.add(currencyPairItem10);
        currencyPairItemList.add(currencyPairItem11);

        currencyPairListMutableLiveData.setValue(currencyPairItemList);

        final android.os.Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (CurrencyPairItem item : currencyPairItemList) {
                    item.generateChangePercentage();
                    System.out.println(" change is : " + item.getChangeStatus());
                }
                currencyPairListMutableLiveData.setValue(currencyPairItemList);
                handler.postDelayed(this, getTheSelectedIntervalInMillisecond());
            }
        }, getTheSelectedIntervalInMillisecond());

    }


    public MutableLiveData<Integer> getTheSelectedInterval() {
        if (theSelectedInterval.getValue() == null) {
            theSelectedInterval.setValue(PreferencesManager.getInstance().getTheSelectedInterval());
        }
        return theSelectedInterval;
    }

    public Integer getTheSelectedIntervalInMillisecond() {
        if (theSelectedInterval.getValue() == null) {
            theSelectedInterval.setValue(PreferencesManager.getInstance().getTheSelectedInterval());
        }
        return theSelectedInterval.getValue() * 1000;
    }

    public void setTheSelectedInterval(Integer theSelectedInterval) {
        this.theSelectedInterval.setValue(theSelectedInterval);
    }
}
