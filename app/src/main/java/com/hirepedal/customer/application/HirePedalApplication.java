package com.hirepedal.customer.application;

import android.app.Application;

public class HirePedalApplication extends Application {

    String TAG = HirePedalApplication.class.getSimpleName();

    com.hirepedal.customer.application.ApplicationComponent component;

    private static HirePedalApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        component = DaggerApplicationComponent.builder().hirePedalModule(new HirePedalModule(this)).build();
    }

    public static synchronized HirePedalApplication getInstance() {
        return mInstance;
    }

    public com.hirepedal.customer.application.ApplicationComponent getComponent() {
        return component;
    }
}
