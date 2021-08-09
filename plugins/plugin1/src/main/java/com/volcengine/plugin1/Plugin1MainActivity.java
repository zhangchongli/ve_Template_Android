package com.volcengine.plugin1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Plugin1MainActivity extends Activity {

    private TextView mVersionTv;
    private Button mFinishSelfBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mira_main);
        mVersionTv = findViewById(R.id.tv_version);
        try {
            int versionCode = Plugin1MainActivity.this.getPackageManager()
                    .getPackageInfo("com.volcengine.plugin3", 0).versionCode;
            mVersionTv.setText("版本：" + versionCode);
        } catch (Throwable ignore) {
        }

        // 调用kotlin代码
        Plugin1Utils.INSTANCE.printTest();

        mFinishSelfBtn = findViewById(R.id.btn_finishSelf);
        mFinishSelfBtn.setOnClickListener(v -> {
            //数据是使用Intent返回
            Intent intent = new Intent();
            //把返回数据存入Intent
            intent.putExtra("result", "My name is mira");
            //设置返回数据
            Plugin1MainActivity.this.setResult(RESULT_OK, intent);//RESULT_OK为自定义常量
            //关闭Activity
            Plugin1MainActivity.this.finish();
        });
    }
}
