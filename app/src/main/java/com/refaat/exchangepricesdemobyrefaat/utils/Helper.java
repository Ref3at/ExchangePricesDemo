package com.refaat.exchangepricesdemobyrefaat.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Helper {


    public static String getDateString() {
        ;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getDefault());
        df.applyPattern("EEE, MMM d");
//        df.applyPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(Calendar.getInstance().getTime());

        return formattedDate;
    }
}
