package com.ss.android.init.tasks

import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.bytedance.lego.init.model.InitTaskProcess
import com.bytedance.veh5.VEH5
import com.bytedance.veh5.config.VEH5Config
import java.util.regex.Pattern

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
        VEH5.initialize(
            VEH5Config.Builder()
                .enableDebug(true)
                .setDeviceId("7135843788734")
                .setOfflineResourceAccessKey("8ecab73a199be5a37313d24a45c5a3cd")
                .setOfflineResourceUrlPrefixes(getCachePrefixes())
        )
    }

    private fun getCachePrefixes() = listOf(
        Pattern.compile("https://mars-jsbridge.goofy-web.bytedance.com/"),
        Pattern.compile("https://sf6-scmcdn-tos.pstatp.com/goofy/")
    )
}