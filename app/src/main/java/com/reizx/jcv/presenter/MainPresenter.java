package com.reizx.jcv.presenter;

import com.reizx.jcv.contract.MainContract;
import com.reizx.jcv.model.DataManager;
import com.reizx.jcv.presenter.common.BasePresenterImpl;

import javax.inject.Inject;

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainPresenter(DataManager dm) {
        super(dm);
    }
}
