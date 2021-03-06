package com.reizx.jcv.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.reizx.jcv.bean.event.TipEvent;
import com.reizx.jcv.component.RxBus;
import com.reizx.jcv.constant.Constants;
import com.reizx.jcv.contract.HomeConstract;
import com.reizx.jcv.bean.event.IpStatusEvent;
import com.reizx.jcv.model.DataManager;
import com.reizx.jcv.presenter.common.BasePresenterImpl;
import com.reizx.jcv.service.ForegroundService;
import com.reizx.jcv.util.JcvLog;
import com.reizx.jcv.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class HomePresenter extends BasePresenterImpl<HomeConstract.View> implements HomeConstract.Presenter{
    @Inject
    public HomePresenter(DataManager dm) {
        super(dm);
    }

    /**
     * 启动ZK服务
     * @param context app的context
     */
    @Override
    public void startZkService(Context context) {
        Intent intentZkService  = new Intent(context, ForegroundService.class);
        intentZkService.setAction(Constants.FORGROUND_SERVICE_ACTION);
        context.startService(intentZkService);
    }

    @Override
    public void stopZkService(Context context) {
        Intent intentZkService  = new Intent(context, ForegroundService.class);
        intentZkService.setAction(Constants.FORGROUND_SERVICE_ACTION);
        context.stopService(intentZkService);
    }

    @Override
    public void bindZkService() {

    }

    @Override
    public void callHelloZkService() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void showCurrentIp() {
        //view.setCurrentIp();
        JcvLog.d("showCurrentIp...");
        //view.showTip(QMUITipDialog.Builder.ICON_TYPE_LOADING, "正在请求");
        RxBus.getInstance().post(new TipEvent(view.getClass().getName(), TipEvent.TipAction.SHOW, QMUITipDialog.Builder.ICON_TYPE_LOADING, "正在请求"));
        dm.getIpApi().getCurrentIp()
                .compose(RxUtil.<ResponseBody>rxSchedulerHelper())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String result = new String(responseBody.bytes(), "GB2312");
                        //todo 此处显示第二个TIP
                        RxBus.getInstance().post(new TipEvent(view.getClass().getName(), TipEvent.TipAction.DISMISS, -1, null));
                        RxBus.getInstance().postDelay(new TipEvent(view.getClass().getName(), TipEvent.TipAction.DISMISS, -1, null), 5000);//销毁前一个TIP
                        RxBus.getInstance().post(new TipEvent(view.getClass().getName(), TipEvent.TipAction.SHOW, QMUITipDialog.Builder.ICON_TYPE_SUCCESS, "请求成功"));//生成新TIP
                        RxBus.getInstance().postDelay(new TipEvent(view.getClass().getName(), TipEvent.TipAction.DISMISS, -1, null), 500);//延迟销毁TIP
                        view.setCurrentIp(result);
                        String timestamp = "" + System.currentTimeMillis();
                        RxBus.getInstance().post(new IpStatusEvent(timestamp, result));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        JcvLog.d("get something error : " + throwable.toString());
                    }
                });

    }
}
