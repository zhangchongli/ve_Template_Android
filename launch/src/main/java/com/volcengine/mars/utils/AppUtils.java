package com.volcengine.mars.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.volcengine.mars.launch.BuildConfig;
import com.volcengine.mars.log.MarsLog;
import com.volcengine.mars.app.LaunchApplication;

/**
 * created by luoqiaoyou on 2020/5/11.
 */
public class AppUtils {

    public static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            MarsLog.e("isDebug", e);
        }
        return BuildConfig.DEBUG;
    }
}
