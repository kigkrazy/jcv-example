package com.reizx.jcv.contract;

import com.reizx.jcv.presenter.common.IBasePresenter;
import com.reizx.jcv.view.common.BaseView;

public class SettingContract {
    public interface View extends BaseView {
        void showIpStatus(String msg);
    }

    public interface  Presenter extends IBasePresenter<View> {
    }
}
