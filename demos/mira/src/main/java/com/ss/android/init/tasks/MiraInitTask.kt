package com.ss.android.init.tasks

import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.bytedance.mira.Mira
import com.bytedance.mira.MiraParam.Builder
import com.volcengine.mars.app.LaunchApplication

@InitTask(
    id = "MiraInitTask",
    desc = "mira初始化",
    moduleName = "mira",
    earliestPeriod = InitPeriod.APP_SUPER2ATTACHBASEEND,
    latestPeriod = InitPeriod.APP_SUPER2ATTACHBASEEND
)
class MiraInitTask : IInitTask() {

    override fun run() {
        val builder = Builder().withDebug(true)
        Mira.init(LaunchApplication.sApplication, builder.build())
    }
}