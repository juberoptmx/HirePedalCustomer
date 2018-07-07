package com.hirepedal.customer.application;

import com.hirepedal.customer.activities.RootActivity;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {HirePedalModule.class})
public interface ApplicationComponent {

    void inject(RootActivity activity);
}
