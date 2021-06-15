package com.ss.android.init.tasks

import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.volcengine.mars.app.LaunchApplication

@InitTask(
    id = "HotfixInitTask",
    desc = "hotfix初始化",
    moduleName = "hotfix",
    earliestPeriod = InitPeriod.APP_ATTACHBASE2SUPER,
    latestPeriod = InitPeriod.APP_ATTACHBASE2SUPER
)
class HotfixInitTask : IInitTask() {

    override fun run() {
        //init hotfix sdk as soon as possible
        HotfixHelper.getInstance().init(LaunchApplication.sApplication)
    }
}