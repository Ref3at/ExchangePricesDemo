package com.refaat.exchangepricesdemobyrefaat.utils;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

public interface Widget extends Serializable {
    View createView(Context context);

    View.OnClickListener getOnClickListener();
}