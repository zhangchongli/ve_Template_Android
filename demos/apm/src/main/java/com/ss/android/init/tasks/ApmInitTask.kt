package com.ss.android.init.tasks

import android.content.Context
import com.apm.insight.AttachUserData
import com.apm.insight.MonitorCrash
import com.bytedance.apm.insight.ApmInsight
import com.bytedance.apm.insight.ApmInsightInitConfig
import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.volcengine.onekit.OneKitApp

@InitTask(
    id = "ApmInitTask",
    desc = "apm初始化",
    moduleName = "apm",
    earliestPeriod = InitPeriod.APP_ONCREATE2SUPER,
    latestPeriod = InitPeriod.APP_ONCREATE2SUPER
)
/**
 * need to make sure this initTask is after RangersApplog
 * */
class ApmInitTask : IInitTask() {

    val aid = "000000";

    override fun run() {
        val context = OneKitApp.getInstance().get(Context::class.java)
        /**
         * Initialize performance-related functions
         * */
        val builder = ApmInsightInitConfig.builder()
            .aid(aid)
            .blockDetect(true)
            .seriousBlockDetect(true)
            .fpsMonitor(true)
            .enableWebViewMonitor(true)
            .memoryMonitor(true)
            .batteryMonitor(true)
            .debugMode(true)
        ApmInsight.getInstance().init(context, builder.build());

        /**
         * ApmInsight崩溃监控初始化
         * */
        val crash = MonitorCrash.init(context, aid, 1, "1.0.0")
            ?.setCustomDataCallback(AttachUserData {
                val map = java.util.HashMap<String, String>()
                map["app_custom"] = "app_value"
                map
            })
        if (crash != null) {
            crash.config().setChannel("channel")
//            crash.config().setDeviceId("did");//可选，可以设置自定义did,不设置会使用内部默认的
//            crash.setReportUrl("www.xxx.com"); // 私有化部署：私有化部署才配置上报地址
            crash.addTags("key", "value") // 自定义筛选tag, 按需添加、可多次覆盖
        }
    }
}