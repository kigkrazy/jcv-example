package com.reizx.jcv.di.component;

import android.app.Service;

import com.reizx.jcv.di.module.ServiceModule;
import com.reizx.jcv.di.scope.ServiceScope;
import com.reizx.jcv.service.ForegroundService;

import dagger.Component;

/**
 * Created by kigkrazy on 18-5-12.
 */

@ServiceScope
@Component(dependencies = AppComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    Service getService();

    void inject(ForegroundService foregroundService);
}
