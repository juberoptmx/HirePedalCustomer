package com.hirepedal.customer.application;

import dagger.Module;

@Module
public class HirePedalModule {

    private final static String TAG = HirePedalModule.class.getSimpleName();

    private final HirePedalApplication mHirePedalApplication;


    public HirePedalModule(HirePedalApplication hirePedalApplication) {
        this.mHirePedalApplication = hirePedalApplication;
    }

}
