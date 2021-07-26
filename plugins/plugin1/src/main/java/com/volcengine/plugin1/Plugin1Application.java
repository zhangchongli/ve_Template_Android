package com.volcengine.plugin1;

import android.app.Application;
import android.content.Context;
import com.volcengine.mira.IPlugin1Service;
import com.volcengine.mira.ServiceManager;

public class Plugin1Application extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceManager.resisterService(IPlugin1Service.class, new Plugin1ServiceImpl());
    }
}
