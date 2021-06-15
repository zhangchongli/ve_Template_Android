package com.volcengine.mars.demo.h5.custom

import android.app.AlertDialog
import android.content.Context
import com.bytedance.ies.xbridge.XBridgeMethod
import com.bytedance.ies.xbridge.XBridgePlatformType
import com.bytedance.ies.xbridge.XReadableMap
import com.bytedance.ies.xbridge.bridgeInterfaces.XCoreBridgeMethod
import com.volcengine.mars.demo.h5.R

/**
 * for
 * create at 2021/4/25
 * @author zhangzongxiang
 */
class OpenDialogMethod : XCoreBridgeMethod() {

    override val name: String = "o.openDialog"

    override fun handle(params: XReadableMap, callback: XBridgeMethod.Callback, type: XBridgePlatformType) {

        val context = contextProviderFactory?.provideInstance(Context::class.java)
                ?: return onFailure(callback, XBridgeMethod.FAIL, "context not provided in host")

        val content = params.getString("content")

        AlertDialog.Builder(context)
                .setMessage(content)
                .setNegativeButton(R.string.btn_close) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()

        onSuccess(callback, mutableMapOf<String, Any>().apply {
            put("time", System.currentTimeMillis())
            put("result", "open success")
        })
    }
}