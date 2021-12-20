package com.refaat.exchangepricesdemobyrefaat;

import android.app.Application;
import android.util.Log;

import com.refaat.exchangepricesdemobyrefaat.utils.PreferencesManager;
import com.scichart.extensions.builders.SciChartBuilder;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        setUpSciChartLicense();
        SciChartBuilder.init(this);
        PreferencesManager.initializeInstance(getApplicationContext());



    }

    private void setUpSciChartLicense() {
        // Set your license code here to license the SciChart Android Examples app
        // You can get a trial license key from https://www.scichart.com/licensing-scichart-android/
        // Purchased license keys can be viewed at https://www.scichart.com/profile
        //
        // e.g.
        //
        // com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey(
        //        "<LicenseContract>" +
        //                "<Customer>your-email@company.com</Customer>" +
        //                "<OrderId>Trial</OrderId>" +
        //                "<LicenseCount>1</LicenseCount>" +
        //                "<IsTrialLicense>true</IsTrialLicense>" +
        //                "<SupportExpires>12/21/2017 00:00:00</SupportExpires>" +
        //                "<ProductCode>SC-ANDROID-2D-ENTERPRISE-SRC</ProductCode>" +
        //                "<KeyCode>6ccc960b22b7b12360a141a7c2a89bce4e40.....09744b6c195022e9fa1ebcf9a0e78167cbaa8f9b8eee9221</KeyCode>" +
        //        "</LicenseContract>"
        // );




        try {

            // Set this code once in MainActivity or application startup
            com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey("yaSzpFPxC2dvj8nuHsmB8QXdk/v9yYL9c0F8pBpz3Nw99z0w+Nh/DIzdLafM/XqtQ4L9OxWp1ISNSiQJbODf94tOShGlzz+iRUBHS/u/KA2u9zzyNk/LbRh39TqiJcsKrvuHa1d3sjx1upO2mvCUftvafxNyk07Wqh+2r0ZrmpdN4saR5GQ0iL97aq8LlVUWnrjWtmPFUusMetZ4l3ZalfdNalupuDkqGjpyrUZ0EL47haN9oLEAUXN3bNlHqZ4+nws1AkYpAokHFj0aDNRVeMayThlZZjQMCuL41DLo7xf/dfgSkcMmgWKMyLtH9IUR+xqxYAMFkvr+L/nsLP1vZLxiBByDzbHSsdHVZnpwZs4uH8Pu6VsLgavz1IPmCLRsQ8dqTO8yqnsQmAX6dXXhpY+ERsGjmmX664UNuCQ90s0wZfujEal8r0XYhkpDfBpsCkgBIuNZjHJX4BpUeXmhovNwh02V1SDZ5OHIFEzBKt07dy3lNdlEaBBaDfw8Zl0ffGmHZ4nM3z2FYIM22uCgBDWZPMcCW+q2Ff4dhXtvcz7blbQKq5O7fyGDkYjL2N8alq52KhbqbThS1NY/");

//            com.scichart.charting.visuals.SciChartSurface.setRuntimeLicenseKey("");
        } catch (Exception e) {
            Log.e("SciChart", "Error when setting the license", e);
        }
    }

}
