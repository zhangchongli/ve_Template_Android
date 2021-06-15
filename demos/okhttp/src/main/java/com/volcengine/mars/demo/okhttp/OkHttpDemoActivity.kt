package com.volcengine.mars.demo.okhttp

import android.os.Bundle
import com.volcengine.mars.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_ok_http_demo.response_text
import kotlinx.android.synthetic.main.activity_ok_http_demo.send_request_button
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.concurrent.thread

class OkHttpDemoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http_demo)
        send_request_button.setOnClickListener {
            sendRequestWithOkHttp()
        }
    }

    private fun sendRequestWithOkHttp(){
        thread{
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://www.bytedance.com/zh/")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body()?.string()
                if(responseData!= null){
                    showResponse(responseData)
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    private fun showResponse(response:String){
        runOnUiThread {
            response_text.setText(response)
        }
    }
}
