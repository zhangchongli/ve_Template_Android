package com.volcengine.mira;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bytedance.mira.Mira;
import com.bytedance.mira.MiraPluginListActivity;
import com.bytedance.morpheus.Morpheus;
import com.bytedance.morpheus.MorpheusStateManager;
import com.bytedance.morpheus.core.MorpheusStateListener;
import com.bytedance.morpheus.core.constant.MorpheusStatusCode;
import com.bytedance.morpheus.mira.MiraMorpheusHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MiraDemoActivity extends AppCompatActivity {
    private Button mToPluginListBtn;
    private Button mToPlugin1Btn;
    private Button mToPlugin2Btn;
    private Button mToPlugin3Btn;
    private Button mDownloadPlugin2Btn;
    private Button mPreLoadPlugin1Btn;
    private Button mGetPlugin1FragmentBtn;
    private Button mStartPlugin1ServiceBtn;
    private Button mSendBroadcastToPlugin1Btn;
    private Button mInvokePlugin1ProviderBtn;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 所有插件状态列表页面
        mToPluginListBtn = findViewById(R.id.btn_toPluginListPage);
        mToPluginListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MiraDemoActivity.this, MiraPluginListActivity.class);
            startActivity(intent);
        });

        // 宿主调用插件 -- 通过Activity跳转到插件1
        mToPlugin1Btn = findViewById(R.id.btn_toPlugin1);
        mToPlugin1Btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClassName("com.volcengine.plugin1", "com.volcengine.plugin1.Plugin1MainActivity");
            startActivityForResult(intent, 1);
        });


        mPreLoadPlugin1Btn = findViewById(R.id.btn_preLoadPlugin1);
        mPreLoadPlugin1Btn.setOnClickListener(v -> {
            MiraMorpheusHelper.downloadAndInstall("com.volcengine.plugin1");
            if (Mira.isPluginInstalled("com.volcengine.plugin1")) {
                if (Mira.isPluginLoaded("com.volcengine.plugin1")) {
                    Toast.makeText(MiraDemoActivity.this, "插件1已加载", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MiraDemoActivity.this, "插件1预加载"
                            + (Mira.loadPlugin("com.volcengine.plugin1") ? "成功" : "失败"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MiraDemoActivity.this, "请安装插件1后再加载", Toast.LENGTH_SHORT).show();
                if (!MiraMorpheusHelper.alreadyRequestPluginConfig()) {
                    // 走到这里可能是启动时接口挂掉了，尝试再次拉取插件
                    MiraMorpheusHelper.asyncFetchPlugins();
                }
            }

        });

        // 宿主调用插件 -- 获取展示插件1的Fragment
        mGetPlugin1FragmentBtn = findViewById(R.id.btn_getPlugin1Fragment);
        mGetPlugin1FragmentBtn.setOnClickListener(v -> {
            Mira.loadPlugin("com.volcengine.plugin1");
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

        // 宿主调用插件 -- 打开插件1的Service
        mStartPlugin1ServiceBtn = findViewById(R.id.btn_startPlugin1Service);
        mStartPlugin1ServiceBtn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClassName("com.volcengine.plugin1", "com.volcengine.plugin1.Plugin1Service");
            startService(intent);
        });

        // 宿主调用插件 -- 发送广播至插件1
        mSendBroadcastToPlugin1Btn = findViewById(R.id.btn_sendBroadcastToPlugin1);
        mSendBroadcastToPlugin1Btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction("com.volcengine.testplugin1receiver");
            sendBroadcast(intent);
        });

        // 宿主调用插件 -- 调用插件1的Provider
        mInvokePlugin1ProviderBtn = findViewById(R.id.btn_invokePlugin1Provider);
        mInvokePlugin1ProviderBtn.setOnClickListener(v -> {
            MiraDemoActivity.this.getContentResolver().query(Uri.parse("content://com.volcengine.testplugin1provider"),
                    null, null, null, null);
        });

        // 插件2目前在POC为手动类型，点击下载按钮手动下载安装插件2
        mDownloadPlugin2Btn = findViewById(R.id.btn_downloadPlugin2);
        mDownloadPlugin2Btn.setOnClickListener(v -> {
            String plugin2PackageName = "com.volcengine.plugin2";
            if (!Mira.isPluginInstalled(plugin2PackageName)){
                MiraMorpheusHelper.downloadAndInstall(plugin2PackageName);
                Toast.makeText(MiraDemoActivity.this, "开始下载插件2\n" + "请在插件状态列表查看下载情况", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MiraDemoActivity.this, "插件2已存在", Toast.LENGTH_SHORT).show();
            }
        });

        // 宿主调用插件 -- 通过Activity跳转到插件2
        mToPlugin2Btn = findViewById(R.id.btn_toPlugin2);
        mToPlugin2Btn.setOnClickListener(v -> {
            String plugin2PackageName = "com.volcengine.plugin2";
            if (!Mira.isPluginLoaded(plugin2PackageName)) {
                Mira.loadPlugin(plugin2PackageName);
            }
            Intent intent = new Intent();
            intent.setClassName(plugin2PackageName, "com.volcengine.plugin2.Plugin2MainActivity");
            startActivity(intent);
        });

        // 按需下载插件
        mToPlugin3Btn = findViewById(R.id.btn_toPlugin3);
        mToPlugin3Btn.setOnClickListener(this::onClick);

    }

    private ProgressDialog mWaitingDialog = null;
    private MorpheusStateListener mMorpheusStateListener = null;

    private void onClick(View v) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (MiraMorpheusHelper.hasNewPlugin("com.volcengine.plugin3")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 判断是否有新版本的插件，有新版本强制更新
                            mWaitingDialog = new ProgressDialog(MiraDemoActivity.this);
                            mWaitingDialog.setTitle("更新插件");
                            mWaitingDialog.setIndeterminate(true);
                            mWaitingDialog.setCancelable(false);

                            Morpheus.install("com.volcengine.plugin3");

                            mMorpheusStateListener = state -> {
                                if ("com.volcengine.plugin3".equals(state.getPackageName())) {
                                    if (state.getStatus() == MorpheusStatusCode.DOWNLOADING) {
                                        if (!mWaitingDialog.isShowing()) {
                                            mWaitingDialog.show();
                                        }

                                        // 更新进度
                                        double progressValue = (double) state.getBytesDownloaded() / (double) state.getTotalBytesToDownload() * 100;
                                        BigDecimal bd = new BigDecimal(progressValue);
                                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                                        mWaitingDialog.setMessage(bd.toString() + "%  " + state.getBytesDownloaded() / 1024 / 1024
                                                + "M / " + state.getTotalBytesToDownload() / 1024 / 1024 + "M");
                                    }

                                    if (state.getStatus() == MorpheusStatusCode.INSTALLED) {
                                        // 安装成功后跳转到插件页面
                                        mWaitingDialog.dismiss();
                                        Intent intent = new Intent();
                                        intent.setClassName("com.volcengine.plugin3", "com.volcengine.plugin3.Plugin3MainActivity");
                                        startActivity(intent);
                                        // 完成后注销回调
                                        MorpheusStateManager.getInstance().removeStateListener(mMorpheusStateListener);
                                    }

                                    if (state.getStatus() == MorpheusStatusCode.FAILED) {
                                        // 失败提示重拾
                                        mWaitingDialog.dismiss();
                                        Toast.makeText(MiraDemoActivity.this, "更新失败请重试\n"
                                                + state.getErrorCode(), Toast.LENGTH_SHORT).show();

                                        // 完成后注销回调
                                        MorpheusStateManager.getInstance().removeStateListener(mMorpheusStateListener);
                                    }
                                }

                            };
                            MorpheusStateManager.getInstance().addStateListener(mMorpheusStateListener);
                        }
                    });

                } else if (Mira.isPluginInstalled("com.volcengine.plugin3")) {
                    Intent intent = new Intent();
                    intent.setClassName("com.volcengine.plugin3", "com.volcengine.plugin3.Plugin3MainActivity");
                    startActivity(intent);
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MiraDemoActivity.this, "无插件3，请在平台配置", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            // 插件1Activity的返回
            try {
                Log.i("mira-demo", "host MiraDemoActivity onActivityResult : " + data.getStringExtra("result"));
            } catch (Throwable ignore) {
            }
        }
    }
}
