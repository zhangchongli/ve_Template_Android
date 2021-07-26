package com.volcengine.plugin3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class Plugin3Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"插件3收到广播", Toast.LENGTH_SHORT).show();
    }
}
