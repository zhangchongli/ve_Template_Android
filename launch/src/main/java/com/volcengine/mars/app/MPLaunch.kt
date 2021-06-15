package com.volcengine.mars.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.annotation.MainThread
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bytedance.lego.init.InitScheduler
import com.bytedance.lego.init.model.InitPeriod
import com.volcengine.mars.log.MarsLog
import com.volcengine.mars.utils.PrivacyUtils

/**
 *
 * created by luoqiaoyou on 2020/8/6.
 */
object MPLaunch {

    @Volatile
    private var start = false
    private val queue = ArrayList<InitPeriod>()
    private val privacyMode
        get() = LaunchApplication.sIsMainProcess
                && PrivacyUtils.isEnable()
                && !PrivacyUtils.isUserAgreed()

    @MainThread
    fun onPeriod(initPeriod: InitPeriod) {
        if (!start) {
            start = true
            init()
        }
        if (privacyMode) {
            queue.add(initPeriod)
        } else {
            InitScheduler.onPeriodStart(initPeriod)
            InitScheduler.onPeriodEnd(initPeriod)
        }
    }

    private fun init() {
        if (privacyMode) {
            val isAgreed = PrivacyUtils.isUserAgreed()
            if (!isAgreed) {
                var filter = IntentFilter().apply {
                    addAction("privacy_agreed")
                }
                LocalBroadcastManager.getInstance(LaunchApplication.sApplication)
                    .registerReceiver(object : BroadcastReceiver() {
                        override fun onReceive(context: Context, intent: Intent) {
                            val action = intent.action
                            if ("privacy_agreed" == action) {
                                MarsLog.i("MARSInit", "received user agreed action then call execute")
                                LocalBroadcastManager.getInstance(LaunchApplication.sApplication)
                                    .unregisterReceiver(this)
                                // execute all tasks
                                InitScheduler.initTasks()
                                replay()
                            }
                        }
                    }, filter)
            }
        } else {
            // execute all tasks
            InitScheduler.initTasks()
        }
    }

    private fun replay() {
        queue.forEach {
            onPeriod(it)
        }
        queue.clear()
    }
}