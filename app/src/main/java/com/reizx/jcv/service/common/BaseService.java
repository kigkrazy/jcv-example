package com.reizx.jcv.service.common;

import android.app.Service;

import com.reizx.jcv.app.App;
import com.reizx.jcv.di.component.DaggerServiceComponent;
import com.reizx.jcv.di.component.ServiceComponent;
import com.reizx.jcv.di.module.ServiceModule;
import com.reizx.jcv.model.DataManager;

import javax.inject.Inject;

public abstract class BaseService extends Service {
    @Inject
    protected App app;

    @Inject
    protected DataManager dm;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected abstract void initInject();

    protected ServiceComponent getServiceComponent(){
        return DaggerServiceComponent.builder()
                .appComponent(App.getAppComponent())
                .serviceModule(new ServiceModule(this))
                .build();
    }
}
