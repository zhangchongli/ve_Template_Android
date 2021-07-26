package com.volcengine.plugin1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Plugin1Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("mira-demo", "Plugin1Receiver onReceive");
        Toast.makeText(context, "插件1收到广播", Toast.LENGTH_SHORT).show();
    }
}
