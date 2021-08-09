package com.volcengine.plugin2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.volcengine.mira.HostUtils;
import com.volcengine.mira.IPlugin1Service;
import com.volcengine.mira.ServiceManager;

public class Plugin2MainActivity extends AppCompatActivity {

    private Button mInvokeHostCodeBtn;
    private Button mInvokeHostResBtn;
    private Button mToHostActivityBtn;
    private TextView mVersionTv;
    private Button mGetPlugin1FragmentBtn;
    private Button mToPlugin1ActivityBtn;
    private Button mStartPlugin1ServiceBtn;
    private Button mSendBroadcastToPlugin1Btn;
    private Button mInvokePlugin1ProviderBtn;


    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mira_main);

        mVersionTv = findViewById(R.id.tv_version);
        try {
            int versionCode = Plugin2MainActivity.this.getPackageManager()
                    .getPackageInfo("com.volcengine.plugin2", 0).versionCode;
            mVersionTv.setText("版本：" + versionCode);
        } catch (Throwable ignore) {
        }


        mInvokeHostCodeBtn = findViewById(R.id.btn_invokeHostCode);
        mInvokeHostCodeBtn.setOnClickListener(v -> {
            // 插件调用宿主的代码
            HostUtils.showSources(Plugin2MainActivity.this);
        });


        mInvokeHostResBtn = findViewById(R.id.btn_invokeHostRes);
        mInvokeHostResBtn.setOnClickListener(v -> {
            // 插件调用宿主的资源
            Toast.makeText(Plugin2MainActivity.this,
                    "我是宿主的资源：" + Plugin2MainActivity.this.getString(R.string.host_string),
                    Toast.LENGTH_LONG).show();
        });

        mToHostActivityBtn = findViewById(R.id.btn_toHostActivity);
        mToHostActivityBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClassName("com.volcengine.mira", "com.volcengine.mira.MiraDemoActivity");
            startActivity(intent);
        });

        // 插件调用插件 -- 获取展示插件1的Fragment
        mGetPlugin1FragmentBtn = findViewById(R.id.btn_getPlugin1Fragment);
        mGetPlugin1FragmentBtn.setOnClickListener(v -> {
            IPlugin1Service plugin1Service = ServiceManager.getService(IPlugin1Service.class);
            if (plugin1Service != null) {
                Fragment fragment = plugin1Service.getFragment();
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.add(R.id.fl_show_fragment, fragment);
                mFragmentTransaction.commit();
                mFragmentTransaction.show(fragment);
            }
        });

        // 插件调用插件 -- 获取展示插件1的Fragment
        mToPlugin1ActivityBtn = findViewById(R.id.btn_toPlugin1Activity);
        mToPlugin1ActivityBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClassName("com.volcengine.plugin1", "com.volcengine.plugin1.Plugin1MainActivity");
            startActivity(intent);//startActivityForResult
        });

        // 插件调用插件 -- 打开插件1的Service
        mStartPlugin1ServiceBtn = findViewById(R.id.btn_startPlugin1Service);
        mStartPlugin1ServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClassName("com.volcengine.plugin1", "com.volcengine.plugin1.Plugin1Service");
            startService(intent);
        });

        // 插件调用插件 -- 发送广播至插件1
        mSendBroadcastToPlugin1Btn = findViewById(R.id.btn_sendBroadcastToPlugin1);
        mSendBroadcastToPlugin1Btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.volcengine.testplugin1receiver");
            sendBroadcast(intent);
        });

        // 插件调用插件 -- 调用插件1的Provider
        mInvokePlugin1ProviderBtn = findViewById(R.id.btn_invokePlugin1Provider);
        mInvokePlugin1ProviderBtn.setOnClickListener(v -> {
            Plugin2MainActivity.this.getContentResolver().query(Uri.parse("content://com.volcengine.testplugin1provider"),
                    null, null, null, null);
        });

    }
}