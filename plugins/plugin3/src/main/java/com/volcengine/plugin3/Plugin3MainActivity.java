package com.volcengine.plugin3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Plugin3MainActivity extends AppCompatActivity {

    private TextView mVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVersionTv = findViewById(R.id.tv_version);

        try {
            int versionCode = Plugin3MainActivity.this.getPackageManager()
                    .getPackageInfo("com.volcengine.plugin3", 0).versionCode;
            mVersionTv.setText("版本：" + versionCode);
        } catch (Throwable ignore) {
        }
    }
}