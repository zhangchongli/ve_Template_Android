package com.ss.android.init.tasks

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.bytedance.lego.init.annotation.InitTask
import com.bytedance.lego.init.model.IInitTask
import com.bytedance.lego.init.model.InitPeriod
import com.bytedance.morpheus.Morpheus
import com.bytedance.morpheus.core.constant.MorpheusStatusCode
import com.bytedance.morpheus.mira.AbsMorpheusAdapter
import com.volcengine.mars.app.LaunchApplication

@InitTask(
    id = "MorpheusInitTask",
    desc = "Morpheus初始化",
    moduleName = "mira",
    earliestPeriod = InitPeriod.APP_ONCREATE2SUPER,
    latestPeriod = InitPeriod.APP_ONCREATE2SUPER
)
class MorpheusInitTask : IInitTask() {

    override fun run() {
        initMorpheus()
    }

    private fun initMorpheus() {
        Morpheus.addStateListener { state ->
            if ("com.bytedance.plugin2" == state.packageName && state.status == MorpheusStatusCode.INSTALLED) {
                Toast.makeText(LaunchApplication.sApplication, "插件2安装成功", Toast.LENGTH_SHORT).show()
            }
            Log.d("mira-demo", state.toString())
        }
        Morpheus.init(object : AbsMorpheusAdapter() {
            override fun getApplication(): Application {
                return LaunchApplication.sApplication
            }
        })
    }
}
