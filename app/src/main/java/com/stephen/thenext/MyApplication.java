package com.stephen.thenext;

import android.app.Application;
import android.content.Context;

/**
 * Created by Stephen on 2015/9/19.
 */
public class MyApplication extends Application {
    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
