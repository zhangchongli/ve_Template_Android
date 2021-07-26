package com.volcengine.mars.demo.downloader

import android.Manifest.permission
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ss.android.socialbase.downloader.depend.AbsDownloadListener
import com.ss.android.socialbase.downloader.downloader.Downloader
import com.ss.android.socialbase.downloader.exception.BaseException
import com.ss.android.socialbase.downloader.model.DownloadInfo
import com.volcengine.mars.permissions.PermissionsManager
import com.volcengine.mars.permissions.PermissionsResultAction
import kotlinx.android.synthetic.main.activity_downloader_demo.download_button
import kotlinx.android.synthetic.main.activity_downloader_demo.download_result_text
import kotlinx.android.synthetic.main.activity_downloader_demo.read_text_button
import java.io.File
import kotlin.concurrent.thread

/**
 * @author zhouchentao
 * @date 2021/4/21
 *
 * Demo for downloader sdk
 *
 * This Demo explain how to use downloader sdk to do a simple download task,
 * a default txt file is provide to download and store in the external storage
 * sd card, you need to apply the android.permission.WRITE_EXTERNAL_STORAGE
 * before download otherwise the task will fail and you can get the failure callback
 * if you defined the {@link DownloadListener}, you can find the failure reason
 * in the parameters of this failure callback.
 * */
class DownloaderDemoActivity : AppCompatActivity() {

    private val filename = "downloadtest.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloader_demo)
        download_button.setOnClickListener {
            if (!checkPermission(permission.WRITE_EXTERNAL_STORAGE)) {
                return@setOnClickListener
            }
            sendDownloadRequest()
        }
        read_text_button.setOnClickListener {
            if (!checkPermission(permission.READ_EXTERNAL_STORAGE)) {
                return@setOnClickListener
            }
            showDownloadTxtFile()
        }
    }

    /**
     * handle the permission request callbacks
     * */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode shr 16 and 0xffff == 0) {
            // forwarding request to permission manager
            PermissionsManager.getInstance().notifyPermissionsChange(this, permissions, grantResults)
        }
    }

    private fun showDownloadTxtFile() {
        val file = File(getDir() + File.separator + filename)
        if (!file.exists()) {
            makeToast("no such file, download first")
            return
        }

        thread {
            val text = file.readText()
            runOnUiThread {
                download_result_text.text = text
            }
        }
    }

    private fun sendDownloadRequest() {
        thread {
            Downloader.with(this)
                .url("https://sf1-dycdn-tos.pstatp.com/obj/eden-cn/nuvknupqpld/download_cache/jd.js")
                .name(filename)
                .savePath(getDir())
                .subThreadListener(object : AbsDownloadListener() {
                    override fun onSuccessed(p0: DownloadInfo?) {
                        makeToast("download successful")
                    }

                    override fun onFailed(p0: DownloadInfo?, p1: BaseException?) {
                        makeToast("download fail, find failure reason from parameters of this method")
                    }
                })
                .download()
        }
    }

    private fun getDir(): String {
        //get the internal SD card path
        val dirFile = File(cacheDir, "mars")
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }
        return dirFile.toString()
    }

    private fun makeToast(msg: String) {
        runOnUiThread {
            Toast.makeText(this@DownloaderDemoActivity, msg, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkPermission(vararg permissionArray: String): Boolean {
        if (PermissionsManager.getInstance().hasAllPermissions(this, permissionArray)) {
            return true
        }
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(
            this,
            permissionArray,
            object : PermissionsResultAction() {
                override fun onGranted() {
                    makeToast("granted")
                }

                override fun onDenied(permission: String) {
                    makeToast("denied:$permission")
                }
            }
        )
        return false
    }
}