package com.reizx.jcv.view.fragment;

import android.os.IBinder;
import android.os.RemoteException;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.reizx.jcv.IAndromedaInf;
import com.reizx.jcv.R;
import com.reizx.jcv.component.cv.ImageRecognitionUtil;
import com.reizx.jcv.contract.HomeConstract;
import com.reizx.jcv.presenter.HomePresenter;
import com.reizx.jcv.util.JcvLog;
import com.reizx.jcv.view.common.BaseFragment;

import org.qiyi.video.svg.Andromeda;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeConstract.View {
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @BindView(R.id.tv_app_show_ip_des)
    TextView tvIp;

    @OnClick(R.id.btn_app_start_service)
    public void startZkService() {
        presenter.startZkService(baseActivity);
    }

    @OnClick(R.id.btn_app_stop_service)
    public void stopZkService() {
        presenter.stopZkService(baseActivity);
    }

    @OnClick(R.id.btn_app_request_ip)
    public void requestIp() {
        AppUtils.AppInfo ai = AppUtils.getAppInfo("com.baidu.appsearch");
        presenter.showCurrentIp();
    }

    @OnClick(R.id.btn_app_andromeda_call)
    public void andromedaCall() {
        IBinder binder = Andromeda.with(app).getRemoteService(IAndromedaInf.class);
        if (binder == null) {
            return;
        }
        IAndromedaInf andromedaInf = IAndromedaInf.Stub.asInterface(binder);
        if (andromedaInf == null) {
            return;
        }
        try {
            andromedaInf.remoteCall();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @OnClick(R.id.btn_app_image_recognition)
    public void imageRecognition() {

        String source = "/sdcard/bg2.png";
        String target = "/sdcard/find_2.png";
        String target2 = "/sdcard/find_4.png";

        ResourceUtils.copyFileFromAssets("bg2.png", source);
        ResourceUtils.copyFileFromAssets("find_2.png", target);
        ResourceUtils.copyFileFromAssets("find_4.png", target2);
        ImageRecognitionUtil.MatchResult result  = ImageRecognitionUtil.match(source, target);
        JcvLog.dd("the match : %b ", result.isMatch());
    }


    @Override
    public int getFragmentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initAllMembersView() {
        super.initAllMembersView();
        initTopBar();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    public void initTopBar() {
        mTopBar.setTitle("主页");
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    @Override
    public void setCurrentIp(String ip) {
        tvIp.setText(ip);
    }
}
