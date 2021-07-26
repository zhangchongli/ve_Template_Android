package com.volcengine.mars.demo.qrcode

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.volcengine.mars.permissions.PermissionsManager
import com.volcengine.mars.qrcode.QrCode
import com.volcengine.mars.qrcode.QrCode.ScanCallback
import com.volcengine.onekit.OneKitApp
import com.volcengine.onekit.service.Log

class QrCodeDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_demo)
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

    fun onClick(v: View) {
        when (v.id) {
            R.id.scan -> QrCode.execute(this, ScanCallback { code, result ->
                OneKitApp.INSTANCE.get(Log::class.java).i(TAG, "result_code:${code}, result:$result")
                if (code == ScanCallback.SUCCESS) {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
                } else if (code == ScanCallback.CANCEL) {
                    Toast.makeText(this, "取消扫码", Toast.LENGTH_SHORT).show()
                } else if (code == ScanCallback.FAIL_PERMISSION) {
                    Toast.makeText(this, "缺少摄像头权限", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        private const val TAG = "QrCodeDemoActivity";
    }
}