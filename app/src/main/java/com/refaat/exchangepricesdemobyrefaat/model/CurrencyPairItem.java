package com.refaat.exchangepricesdemobyrefaat.model;

import com.refaat.exchangepricesdemobyrefaat.utils.ChangeStatus;

import org.apache.commons.math3.util.Precision;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class CurrencyPairItem {


    private int id;
    private String name;
    private double bidPrice;
    private double askPrice;
    private double changePercentage;
    private Date date;



    public CurrencyPairItem(String name, double bidPrice, double askPrice) {
        this.name = name;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
        generateChangePercentage();
    }

    public String getName() {
        return name;
    }

    public double getBidPrice() {
//        System.out.println("Bid changePercentage = " + changePercentage);
        bidPrice =  bidPrice + (bidPrice * changePercentage);

        return Precision.round(bidPrice, 5);
    }

    public double getAskPrice() {
//        System.out.println("Ask changePercentage = " + changePercentage);
        askPrice =  askPrice + (askPrice * changePercentage);
        return Precision.round(askPrice, 5);
    }

    public double generateChangePercentage() {
        if (changePercentage==0){
            changePercentage = (double) Math.round(ThreadLocalRandom.current().nextDouble(-5, 5 + 1)) / 100.00;
            return 0;
        }
        changePercentage = (double) Math.round(ThreadLocalRandom.current().nextDouble(-5, 5 + 1)) / 100.00;
        updateTheDate();
        return changePercentage;
    }

    public ChangeStatus getChangeStatus() {
        if (changePercentage > 0) {
            System.out.println("changePercentage = " + changePercentage + "||| INCREASE");

            return ChangeStatus.INCREASE;
        } else if (changePercentage < 0) {
            System.out.println("changePercentage = " + changePercentage + "||| DECREASE");
            return ChangeStatus.DECREASE;
        } else {
            System.out.println("changePercentage = " + changePercentage + "||| SAME_VALUE");
            return ChangeStatus.SAME_VALUE;
        }
    }


    private void updateTheDate() {
        this.date = Calendar.getInstance().getTime();
    }

    public Date getDate() {
        return date;
    }
}
