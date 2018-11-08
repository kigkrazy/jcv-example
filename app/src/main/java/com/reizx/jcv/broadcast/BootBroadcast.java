package com.reizx.jcv.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.reizx.jcv.util.AsfLog;

/**
 * 开机启动广播
 */
public class BootBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AsfLog.dt(BootBroadcast.class.toString(),"receive BootBroadcast");
    }
}
