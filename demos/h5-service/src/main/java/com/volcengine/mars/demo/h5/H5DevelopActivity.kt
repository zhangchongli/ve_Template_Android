package com.volcengine.mars.demo.h5

import android.annotation.SuppressLint
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
import com.volcengine.mars.demo.h5.custom.CustomHandlerMethod
import kotlinx.android.synthetic.main.activity_h5_base.*
import org.json.JSONObject

class H5DevelopActivity : BaseH5Activity() {

    override fun buildUi() {
        super.buildUi()

        buttonContainer.addView(createSendEventBtn(), LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT))
    }

    @SuppressLint("SetTextI18n")
    private fun createSendEventBtn(): Button = Button(this).apply {
        text = "send Event($EVENT_NAME)"
        isAllCaps = false

        setOnClickListener {
            sendCustomEvent()
        }
    }

    private fun sendCustomEvent() {
        val params = ReadableMapImpl(JSONObject().apply {
            put("time", System.currentTimeMillis())
            put("send event", EVENT_NAME)
        })
        BDH5.sendEvent(Event(EVENT_NAME, System.currentTimeMillis(), params))
    }

    override fun createWebViewContainer(): WebViewContainer = BDH5.obtainWebViewContainer(this, WebViewProvider())!!

    private class WebViewProvider : WebViewCreateProvider() {

        override fun getMethods(): Set<Class<out XBridgeMethod>> = hashSetOf<Class<out XBridgeMethod>>().apply {
            add(CustomHandlerMethod::class.java)
        }
    }

    override fun applySettings() {
        super.applySettings()

        webView.loadUrl("https://mars-jsbridge.goofy-web.bytedance.com/debug#/")

    }

    private companion object {

        private const val EVENT_NAME = "o.customEvent"
    }
}
