package com.volcengine.mars.demo.h5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_h5_main.*

class H5MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5_main)

        openDevelopActivityBtn.setOnClickListener {
            startActivity(Intent(this, H5DevelopActivity::class.java))
        }

        openExampleActivityBtn.setOnClickListener {
            startActivity(Intent(this, H5ExampleActivity::class.java))
        }

        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

}
