package com.volcengine.mars.demo.update;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.volcengine.mars.update.IUpdateForceExit;

public class UpdateForceExitImpl implements IUpdateForceExit {
    public static final String ACTION_EXIT_APP = "com.ss.android.platform.app.action.exit_app";
    @Override
    public void forceExitApp(@NonNull Context context) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(
                new Intent(ACTION_EXIT_APP));
    }
}
