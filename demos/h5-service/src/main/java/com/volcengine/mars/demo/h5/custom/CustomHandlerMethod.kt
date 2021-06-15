package com.volcengine.mars.demo.h5.custom

import android.content.Context
import android.widget.Toast
import com.bytedance.ies.xbridge.XBridgeMethod
import com.bytedance.ies.xbridge.XBridgePlatformType
import com.bytedance.ies.xbridge.XReadableMap
import com.bytedance.ies.xbridge.bridgeInterfaces.XCoreBridgeMethod

/**
 * for
 * create at 2021/4/23
 * @author zhangzongxiang
 */
class CustomHandlerMethod : XCoreBridgeMethod() {


    override val name: String = "o.customMethod"

    override val access: XBridgeMethod.Access = XBridgeMethod.Access.PUBLIC

    override fun handle(
        params: XReadableMap,
        callback: XBridgeMethod.Callback,
        type: XBridgePlatformType
    ) {

        val context = contextProviderFactory?.provideInstance(Context::class.java)
            ?: return onFailure(callback, XBridgeMethod.FAIL, "context not provided in host")

        val content = params.getString("content")

        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()

        val result = mutableMapOf<String, Any>().apply {
            put("time", System.currentTimeMillis())
            put("invoked", "success")
        }

        onSuccess(callback, result)
    }
}