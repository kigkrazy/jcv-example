package com.reizx.jcv.di.component;

import android.app.Activity;

import com.reizx.jcv.di.module.FragmentModule;
import com.reizx.jcv.di.scope.FragmentScope;
import com.reizx.jcv.view.fragment.HomeFragment;
import com.reizx.jcv.view.fragment.MainFragment;
import com.reizx.jcv.view.fragment.SettingFragment;

import dagger.Component;

/**
 * Created by kigkrazy on 18-5-12.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    Activity getActivity();

    void Inject(HomeFragment homeFragment);

    void Inject(SettingFragment settingFragment);

    void Inject(MainFragment settingFragment);
}
