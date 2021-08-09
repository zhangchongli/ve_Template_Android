package com.volcengine.mars.demo.screentools

import android.Manifest.permission
import android.R.layout
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.JPEG
import android.graphics.BitmapFactory
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.volcengine.mars.permissions.PermissionsManager
import com.volcengine.mars.permissions.PermissionsResultAction
import com.volcengine.mars.utils.ScreenAdapterUtils
import com.volcengine.mars.utils.ScreenShotUtils
import kotlinx.android.synthetic.main.activity_screen_tools.screen_dp_button
import kotlinx.android.synthetic.main.activity_screen_tools.screen_dp_spinner
import kotlinx.android.synthetic.main.activity_screen_tools.screen_shot_button
import kotlinx.android.synthetic.main.activity_screen_tools.screen_shot_webview_button
import kotlinx.android.synthetic.main.activity_screen_tools.webView
import java.io.File
import java.io.FileInputStream

class ScreenToolsActivity : AppCompatActivity() {

    private var type = 0
    companion object{
        var targetDp = 380
    }

    @RequiresApi(VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        ScreenAdapterUtils.setCustomDensity(this, this.application, targetDp)
        /**
         * WebView.enableSlowWholeDocumentDraw() is called to avoid incomplete screenshot
         * */
        android.webkit.WebView.enableSlowWholeDocumentDraw()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_tools)

        initData()
        webView.settings.javaScriptEnabled = true;
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.baidu.com")

        screen_dp_button.setOnClickListener {
            when (type) {
                0 -> targetDp = 200
                1 -> targetDp = 380
                2 -> targetDp = 480
                3 -> targetDp = 700
                4 -> targetDp = 1000
                else -> targetDp = 480
            }
            finish();
            startActivity(getIntent());
        }

        screen_shot_button.setOnClickListener {
            if (!checkPermission(permission.WRITE_EXTERNAL_STORAGE)) {
                return@setOnClickListener
            }
            val filePath = ScreenShotUtils.captureScreenAndSave(this, "mars_test_screenshot", null)
            if (filePath != null) {
                Toast.makeText(this, "Screen shot capture successful", Toast.LENGTH_SHORT).show()
            }
        }

        screen_shot_webview_button.setOnClickListener {
            if (!checkPermission(permission.WRITE_EXTERNAL_STORAGE)) {
                return@setOnClickListener
            }
            val filePath = ScreenShotUtils.captureWebViewAndSave(webView, "mars_test_webview_save", JPEG)
            if (filePath != null) {
                Toast.makeText(this, "WebView screen shot capture successful", Toast.LENGTH_SHORT).show()

                checkSavedImage(filePath)
            }
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

    /**
     * An example method to check the saved Image
     * @param filePath
     * @return the Bitmap of this image
     * */
    private fun checkSavedImage(filePath: String) : Bitmap {
        val file = File(filePath)
        val inputStream = FileInputStream(file)
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun initData() {
        val mItems = resources.getStringArray(R.array.dptype)
        val mAdapter = ArrayAdapter(this, layout.simple_spinner_item, mItems)
        screen_dp_spinner.adapter = mAdapter
        screen_dp_spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int,
                id: Long
            ) {
                type = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
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
                    Toast.makeText(this@ScreenToolsActivity, "granted", Toast.LENGTH_SHORT).show()
                }

                override fun onDenied(permission: String) {
                    Toast.makeText(
                        this@ScreenToolsActivity,
                        "denied:$permission",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
        return false
    }
}