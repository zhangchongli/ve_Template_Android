package com.volcengine.mars.demo.h5

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import com.bytedance.bdh5.BDH5
import com.bytedance.bdh5.WebViewCreateProvider
import com.bytedance.ies.xbridge.XBridgeMethod
import com.bytedance.ies.xbridge.event.Event
import com.bytedance.ies.xbridge.platform.web.inner.ReadableMapImpl
import com.bytedance.webx.core.webview.WebViewContainer
import com.volcengine.mars.demo.h5.base.BaseH5Activity
import com.volcengine.mars.demo.h5.custom.OpenDialogMethod
import kotlinx.android.synthetic.main.activity_h5_base.*
import org.json.JSONObject

/**
 * create at 2021/4/25
 * @author zhangzongxiang
 */
class H5ExampleActivity : BaseH5Activity() {

    override fun buildUi() {
        super.buildUi()

        buttonContainer.addView(createOpenJsDialogButton(), LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }

    private fun createOpenJsDialogButton(): Button = Button(this).apply {

        text = getString(R.string.btn_open_js_dialog)
        isAllCaps = false

        setOnClickListener {
            openJsDialog()
        }
    }

    override fun createWebViewContainer(): WebViewContainer = BDH5.obtainWebViewContainer(this, WebViewProvider())!!

    private fun openJsDialog() {
        val params = ReadableMapImpl(JSONObject().apply {
            put("content", getString(R.string.event_content))
        })
        val event = Event(eventName = "o.openJsDialog", timestamp = System.currentTimeMillis(), params = params)
        BDH5.sendEvent(event)
    }

    private class WebViewProvider : WebViewCreateProvider() {

        override fun getMethods(): Set<Class<out XBridgeMethod>> = hashSetOf<Class<out XBridgeMethod>>().apply {
            add(OpenDialogMethod::class.java)
        }

    }

    override fun applySettings() {
        super.applySettings()

        webView.loadUrl("https://mars-jsbridge.goofy-web.bytedance.com/example#/")
    }
}