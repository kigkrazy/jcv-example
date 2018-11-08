package com.reizx.jcv.contract;

import com.reizx.jcv.presenter.common.IBasePresenter;
import com.reizx.jcv.view.common.BaseView;

public class MainContract {
    public interface View extends BaseView {

    }

    public interface Presenter extends IBasePresenter<View> {

    }
}
