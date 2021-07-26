package com.volcengine.plugin1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Plugin1Service extends Service {

    @Override
    public void onCreate() {
        Log.i("mira-demo", "Plugin1Service onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("mira-demo", "Plugin1Service onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("mira-demo", "Plugin1Service onStartCommand");
        Toast.makeText(getApplicationContext(), "插件1服务被访问", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("mira-demo", "Plugin1Service onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("mira-demo", "Plugin1Service onDestroy");
        super.onDestroy();
    }
}
