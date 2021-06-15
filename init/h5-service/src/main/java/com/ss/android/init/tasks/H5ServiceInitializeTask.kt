package com.ss.android.init.tasks

import com.bytedance.bdh5.BDH5
import com.bytedance.bdh5.config.BDH5Config
import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.bytedance.lego.init.model.InitTaskProcess

@InitTask(
    id = "h5-service",
    desc = "h5服务初始化",
    earliestPeriod = InitPeriod.APP_SUPER2ONCREATEEND,
    latestPeriod = InitPeriod.APP_SUPER2ONCREATEEND,
    mustRunInMainThread = false,
    runInProcess = [InitTaskProcess.ALL] // Hybrid framework may be in a separate process
)
class H5ServiceInitializeTask : IInitTask() {

    override fun run() {
        BDH5.initialize(
                BDH5Config.Builder()
                        .enableDebug()
        )
    }
}