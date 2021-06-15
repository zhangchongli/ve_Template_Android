package com.volcengine.mars.utils

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.volcengine.mars.app.LaunchApplication
import com.volcengine.onekit.utils.InitOptionsConst

/**
 * when the privacy policy is activated, initialization is delayed until the user agrees
 * created by luoqiaoyou on 2020/8/10.
 */
object PrivacyUtils {

    val frameworkSp by lazy {
        LaunchApplication.sApplication.getSharedPreferences(
            "mars_framework_sp",
            Context.MODE_MULTI_PROCESS
        )
    }

    @JvmStatic
    fun isUserAgreed(): Boolean {
        return frameworkSp.getBoolean("privacy_agreed", false)
    }

    @JvmStatic
    fun setUserAgreed(agreed: Boolean) {
        frameworkSp.edit().putBoolean("privacy_agreed", agreed).commit()
        LocalBroadcastManager.getInstance(LaunchApplication.sApplication)
            .sendBroadcast(Intent("privacy_agreed"))
    }

    @JvmStatic
    fun isEnable(): Boolean {
        return InitOptionsConst.getBoolean(LaunchApplication.sApplication, "privacy_mode")
    }
}