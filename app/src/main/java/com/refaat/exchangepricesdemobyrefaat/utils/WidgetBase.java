package com.refaat.exchangepricesdemobyrefaat.utils;


import android.view.View;

public abstract class WidgetBase implements Widget{
    protected final int id;
    protected final View.OnClickListener onClickListener;

    public WidgetBase(int id, View.OnClickListener onClickListener) {
        this.id = id;
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }
}
