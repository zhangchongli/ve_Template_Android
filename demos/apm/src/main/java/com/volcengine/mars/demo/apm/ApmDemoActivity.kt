package com.volcengine.mars.demo.apm

import android.os.Bundle
import android.os.SystemClock
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.android.monitor.webview.WebViewMonitorWebChromeClient
import com.bytedance.android.monitor.webview.WebViewMonitorWebViewClient
import com.volcengine.mars.demo.apm.R.layout
import kotlinx.android.synthetic.main.activity_apm_demo.anr_button
import kotlinx.android.synthetic.main.activity_apm_demo.crash_button
import kotlinx.android.synthetic.main.activity_apm_demo.custom_error_button
import kotlinx.android.synthetic.main.activity_apm_demo.stuck_button

class ApmDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_apm_demo)

        initButton()
    }

    private fun initButton() {
        /**
         * Execution causes a crash, and if the application crashes
         * within 8 seconds of startup, it is considered a startup
         * crash and reported
         * */
        crash_button.setOnClickListener {
            throw RuntimeException("Monitor Exception")
        }
        /**
         * After the execution, manually swipe the screen frequently
         * for several seconds, and ANR pop-up window and data report
         * will appear after a few seconds
         * */
        anr_button.setOnClickListener {
            SystemClock.sleep(20000)
        }

        /**
         * Same as crash button
         * */
        custom_error_button.setOnClickListener {
            throw RuntimeException("Monitor Exception")
        }

        stuck_button.setOnClickListener {
            try {
                Thread.sleep(2600)
                testSeriousBlock()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun testSeriousBlock() {
        try {
            Thread.sleep(2600)
        } catch (e: java.lang.Exception) {
        }
    }

    /**
     * WebView page monitoring: You need to set the following methods
     * in the WebView initialization
     *
     * The following function is called when the WebView loads the URL
     * WebViewMonitorHelper.getInstance().onLoadUrl(webView, url);
     * webView.loadUrl(url);
     */
    private fun initWebviewConfig(webView: WebView) {
        //设置webView的WebChromeClient如下或者继承WebViewMonitorWebChromeClient
        webView.webChromeClient = WebViewMonitorWebChromeClient()
        //设置webView的WebViewClient如下或者继承WebViewMonitorWebViewClient
        webView.webViewClient = WebViewMonitorWebViewClient()
    }
}