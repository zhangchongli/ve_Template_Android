package com.ss.android.init.tasks

import android.app.Application
import android.content.Context
import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.bytedance.mira.Mira
import com.bytedance.mira.MiraParam.Builder
import com.volcengine.onekit.OneKitApp

@InitTask(
    id = "MiraInitTask",
    desc = "mira初始化",
    moduleName = "mira",
    earliestPeriod = InitPeriod.APP_ONCREATE2SUPER,
    latestPeriod = InitPeriod.APP_ONCREATE2SUPER
)
class MiraInitTask : IInitTask() {

    override fun run() {
        val builder = Builder().withDebug(true)

        val application = OneKitApp.getInstance().get(Context::class.java) as Application
        Mira.init(application, builder.build())
    }
}