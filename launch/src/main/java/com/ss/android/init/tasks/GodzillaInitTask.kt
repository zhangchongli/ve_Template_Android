package com.ss.android.init.tasks

import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod.APP_SUPER2ATTACHBASEEND
import com.bytedance.platform.godzilla.Godzilla
import com.bytedance.platform.godzilla.anr.CookieManagerPlugin
import com.bytedance.platform.godzilla.anr.SpBlockPlugin
import com.bytedance.platform.godzilla.crash.CrashPluginGroup
import com.bytedance.platform.godzilla.crash.ProviderInstalledFailedPlugin
import com.bytedance.platform.godzilla.crash.SpFetcherDeadObjectPlugin
import com.bytedance.platform.godzilla.plugin.StartType
import com.bytedance.platform.godzilla.sysopt.SysOptPluginGroup
import com.bytedance.platform.godzilla.sysopt.UbsanOptPlugin
import com.volcengine.mars.app.LaunchApplication

/**
 * godzilla task should execute after npth task
 * created by luoqiaoyou on 2020/11/22.
 */
@InitTask(
    id = "godzilla",
    desc = "jvm/system bugfix component",
    earliestPeriod = APP_SUPER2ATTACHBASEEND,
    latestPeriod = APP_SUPER2ATTACHBASEEND,
    mustRunInMainThread = false
)
class GodzillaInitTask : IInitTask() {

    override fun run() {
        val builder = Godzilla.Builder(LaunchApplication.sApplication)
        installAnrPlugin(builder)
        installSysoptPlugin(builder)
        installCashPlugin(builder)
        Godzilla.init(builder.build()).start(StartType.REGISTER_EXCEPTION)
    }

    private fun installAnrPlugin(builder: Godzilla.Builder) {
        builder.plugin(SpBlockPlugin())
        builder.plugin(CookieManagerPlugin())
    }

    private fun installSysoptPlugin(builder: Godzilla.Builder) {
        builder.plugin(SysOptPluginGroup())
        builder.plugin(UbsanOptPlugin())
    }

    private fun installCashPlugin(builder: Godzilla.Builder) {
        builder.plugin(CrashPluginGroup())
        builder.plugin(SpFetcherDeadObjectPlugin())
        builder.plugin(ProviderInstalledFailedPlugin())
    }
}
