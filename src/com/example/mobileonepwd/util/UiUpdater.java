package com.example.mobileonepwd.util;

import android.content.Context;

public abstract class UiUpdater<T> {
    private Context context;

    public abstract void updateUI(T obj);

    //    public UiUpdater() {
//        super();
//    }
    public UiUpdater(Context context) {
        super();
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}