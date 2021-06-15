package com.ss.android.init.tasks

import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.bytedance.veh5.gecko.OfflineResourcesUpdateListener
import com.bytedance.geckox.model.UpdatePackage

/**
 * for
 * create at 2021/5/23
 * @author zhangzongxiang
 */
class CommonUpdateListener(private val context: Context, private val mainHandler: Handler) : OfflineResourcesUpdateListener() {

    override fun onUpdateSuccess(pkg: UpdatePackage?, version: Long) {
        showToast("${pkg?.getChannel()} update success, version = $version")
    }

    override fun onUpdateFailed(pkg: UpdatePackage?, throwable: Throwable?) {
        showToast("${pkg?.getChannel()} update failed ${throwable?.message}")
    }

    override fun onDownloadSuccess(pkg: UpdatePackage?) {
        showToast("${pkg?.getChannel()} download success")
    }

    override fun onDownloadFail(pkg: UpdatePackage?, throwable: Throwable?) {
        showToast("${pkg?.getChannel()} download failed ${throwable?.message}")
    }

    private fun showToast(msg: String) {
        mainHandler.post {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
    
    private companion object {
        private const val TAG = "CommonUpdateListener"
    }
}