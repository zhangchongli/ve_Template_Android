package com.volcengine.mars.demo.h5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.bytedance.veh5.VEH5
import com.bytedance.veh5.debug.DebugTools
import com.ss.android.init.tasks.CommonUpdateListener
import com.volcengine.mars.app.LaunchApplication
import kotlinx.android.synthetic.main.activity_h5_main.*

class H5MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5_main)

        openDevelopActivityBtn.setOnClickListener {
            startActivity(Intent(this, H5DevelopActivity::class.java))
        }

        openExampleOnlineActivityBtn.setOnClickListener {
            VEH5.getGlobalController()?.setOfflineResourcesEnable(false)
            DebugTools.clearOfflineResourcesLoadRecord()
            startActivity(Intent(this, H5ExampleActivity::class.java))
        }

        openExampleOfflineActivityBtn.setOnClickListener {
            VEH5.getGlobalController()?.setOfflineResourcesEnable(true)
            DebugTools.clearOfflineResourcesLoadRecord()
            startActivity(Intent(this, H5ExampleActivity::class.java))
        }

        requestPermissions()

        VEH5.updateOfflineResources(
            listOf("mars-jsbridge"), CommonUpdateListener(
                LaunchApplication.getInstance(), Handler(
                    Looper.getMainLooper())
            )
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

}
