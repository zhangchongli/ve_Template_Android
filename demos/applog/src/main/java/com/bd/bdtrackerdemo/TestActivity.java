package com.bd.bdtrackerdemo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.IOaidObserver;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.volcengine.mars.permissions.PermissionsManager;
import java.util.HashMap;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author shiyanlong
 */
public class TestActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener, OnCheckedChangeListener, OnClickListener,
        OnRatingBarChangeListener, OnSeekBarChangeListener, OnFocusChangeListener,
        CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ((RatingBar) findViewById(R.id.ratingBar)).setOnRatingBarChangeListener(this);
        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(this);
        findViewById(R.id.button_appid).setOnClickListener(this);
        findViewById(R.id.button_did).setOnClickListener(this);
        findViewById(R.id.button_iid).setOnClickListener(this);
        findViewById(R.id.button_ssid).setOnClickListener(this);
        findViewById(R.id.button_oaid).setOnClickListener(this);
        findViewById(R.id.button_udid).setOnClickListener(this);
        findViewById(R.id.button_uuid).setOnClickListener(this);
        findViewById(R.id.button_openudid).setOnClickListener(this);
        findViewById(R.id.button_clientudid).setOnClickListener(this);
        findViewById(R.id.button_send_misc).setOnClickListener(this);
        findViewById(R.id.button_internal_v3_1).setOnClickListener(this);
        findViewById(R.id.button_internal_v3_2).setOnClickListener(this);
        findViewById(R.id.button_send_v1).setOnClickListener(this);
        findViewById(R.id.imageButton).setOnClickListener(this);
        findViewById(R.id.button_ab_sdk_version).setOnClickListener(this);
        findViewById(R.id.button_ab_config).setOnClickListener(this);
        findViewById(R.id.button_ab_config_version).setOnClickListener(this);
        findViewById(R.id.button_ab_exposed).setOnClickListener(this);
        findViewById(R.id.editText).setOnFocusChangeListener(this);
        ((Switch) findViewById(R.id.event_sender_checkbox)).setOnCheckedChangeListener(this);
        findViewById(R.id.button_succ_rate).setOnClickListener(this);
        findViewById(R.id.button_empty_name_event).setOnClickListener(this);
        ((Switch) findViewById(R.id.switch1)).setOnCheckedChangeListener(this);
        ((ToggleButton) findViewById(R.id.toggleButton)).setOnCheckedChangeListener(this);
        boolean newUserMode = AppLog.isNewUserMode(TestActivity.this);
        ((Switch) findViewById(R.id.switch_new_user)).setChecked(newUserMode);
        ((Switch) findViewById(R.id.switch_new_user)).setOnCheckedChangeListener(TestActivity.this);
        findViewById(R.id.button_custom_click_event).setOnClickListener(this);
        findViewById(R.id.button_user_id).setOnClickListener(this);
        findViewById(R.id.button_app_language_and_region).setOnClickListener(this);
        findViewById(R.id.button_set_uuid).setOnClickListener(this);
        findViewById(R.id.button_set_custom).setOnClickListener(this);
        findViewById(R.id.button_set_custom2).setOnClickListener(this);

//        new MyThread().start();
    }

    /**
     * handle the permission request callbacks
     * */
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode >> 16 & 0xffff) == 0) {
            // forwarding request to permission manager
            PermissionsManager.getInstance().notifyPermissionsChange(this, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
        if (buttonView.getId() == R.id.switch_new_user) {
            DemoConfig.updateNewUserModeSp(this, isChecked);
            AppLog.setNewUserMode(this, isChecked);
            try {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear " + getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (buttonView.getId() == R.id.event_sender_checkbox) {
            Toast.makeText(this, "onCheckedChanged: " + buttonView.getClass().getSimpleName() + ", " + isChecked,
                    Toast.LENGTH_SHORT).show();
            String url = ((EditText) findViewById(R.id.et_url)).getText().toString();
            AppLog.setEventSenderEnable(isChecked, this);
        }
        Toast.makeText(this, "onCheckedChanged: " + buttonView.getClass().getSimpleName() + ", " + isChecked,
                Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onCheckedChanged_TestActivity" + buttonView.getClass().getSimpleName(), (JSONObject) null);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        AppLog.onEventV3("onCheckedChanged_TestActivity" + group.findViewById(checkedId).getClass().getSimpleName(),
                (JSONObject) null);
    }

    @Override
    public void onClick(final View v) {
        Toast.makeText(this, "onClick: " + v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onClick_TestActivity" + v.getClass().getSimpleName());
        if (v.getId() == R.id.button_appid) {
            ((Button) v).setText("appid:" + AppLog.getAid());
        } else if (v.getId() == R.id.button_did) {
            ((Button) v).setText("did:" + AppLog.getDid());
        } else if (v.getId() == R.id.button_iid) {
            ((Button) v).setText("iid:" + AppLog.getIid());
        } else if (v.getId() == R.id.button_ssid) {
            ((Button) v).setText(AppLog.getSsid());
        } else if (v.getId() == R.id.button_oaid) {
            AppLog.setOaidObserver(new IOaidObserver() {
                @Override
                public void onOaidLoaded(final Oaid oaid) {
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            ((Button) v).setText(oaid.id);
                        }
                    });
                }
            });
        } else if (v.getId() == R.id.button_uuid) {
            ((Button) v).setText("uuid:" + AppLog.getUserUniqueID());
        } else if (v.getId() == R.id.button_openudid) {
            ((Button) v).setText("openUdid:" + AppLog.getOpenUdid());
        } else if (v.getId() == R.id.button_clientudid) {
            ((Button) v).setText(AppLog.getClientUdid());
        } else if (v.getId() == R.id.button_udid) {
            if ("".equals(AppLog.getUdid())) {
                ((Button) v).setText("udid为空，可能生成早于权限授予，请重启app再试");
            } else {
                ((Button) v).setText("udid:" + AppLog.getUdid());
            }
        } else if (v.getId() == R.id.button_send_misc) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("misc param", 200);
                AppLog.onMiscEvent("type_1", jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (v.getId() == R.id.button_internal_v3_1) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("v3 intenal1", 200);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppLog.onInternalEventV3("some_event", jsonObject,
                    "1443", "second app1", "product type 1");
        } else if (v.getId() == R.id.button_internal_v3_2) {
            Bundle bundle = new Bundle();
            bundle.putInt("v3 intenal2", 300);
            AppLog.onInternalEventV3("some_event", bundle,
                    "1443", "second app2", "product type 2");
        } else if (v.getId() == R.id.button_send_v1) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("test_v1_param", 200);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            AppLog.onEvent("test_category", "test_tag", "test_lable", 0, 0, jsonObject);
        } else if (v.getId() == R.id.button_custom_click_event) {
            AppLog.onEventV3("realtime_click", (JSONObject) null);
        } else if (v.getId() == R.id.button_user_id) {
            AppLog.setUserID(1);
        } else if (v.getId() == R.id.button_ab_sdk_version) {
            AppLog.setExternalAbVersion("experiment-no2");
        } else if (v.getId() == R.id.button_succ_rate) {
            AppLog.onEventV3("succ_rate_event", (JSONObject) null);
        } else if (v.getId() == R.id.button_empty_name_event) {
            AppLog.onEventV3("", (JSONObject) null);
        } else if (v.getId() == R.id.button_ab_config) {
            Toast.makeText(this, "abConfig: " + AppLog.getAbConfig("test", "default"), Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.button_ab_config_version) {
            Toast.makeText(this, "abConfigVersion: " + AppLog.getAbSdkVersion(), Toast.LENGTH_SHORT)
                    .show();
        } else if (v.getId() == R.id.button_ab_exposed) {
            Toast.makeText(this, "ab曝光: " + AppLog.getAbConfig("experiment-no2", ""), Toast.LENGTH_SHORT)
                    .show();
        } else if (v.getId() == R.id.button_set_uuid) {
            AppLog.setUserUniqueID(Integer.toString(new Random().nextInt()));
        } else if (v.getId() == R.id.button_set_custom) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("test1", "test1");
            AppLog.setHeaderInfo(map);
        } else if (v.getId() == R.id.button_set_custom2) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("test2", 1);
            AppLog.setHeaderInfo(map);
        } else if (v.getId() == R.id.button_app_language_and_region) {
            AppLog.setAppLanguageAndRegion("test_langugae", "test_region");
        }
    }

    @Override
    public void onFocusChange(final View v, final boolean hasFocus) {
        Toast.makeText(this, "onFocusChange: " + v.getClass().getSimpleName() + ", " + hasFocus, Toast.LENGTH_SHORT)
                .show();
        AppLog.onEventV3("onFocusChange_TestActivity" + v.getClass().getSimpleName(), (JSONObject) null);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.listView || id == R.id.gridView || id == R.id.expandableListView || id == R.id.gallery
                || id == R.id.spinner || id == R.id.viewPagerS) {
            startActivity(new Intent(this, ListActivity.class).putExtra(ListActivity.KEY_TYPE, id));
        } else if (id == R.id.viewPagerX) {
            startActivity(new Intent(this, ListActivity.class).putExtra(ListActivity.KEY_TYPE, id));
        } else if (id == R.id.fragment) {
            startActivity(new Intent(this, ItemListActivity.class));
        } else if (id == R.id.dialog) {
            Builder builder = new Builder(this);
            builder.setTitle("测试一下弹框?");
            builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    AppLog.onEventV3(
                            "onClick_TestActivity_Dialog" + ((AlertDialog) dialog).getButton(which).getClass()
                                    .getSimpleName(),
                            (JSONObject) null);
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    AppLog.onEventV3(
                            "onClick_TestActivity_Dialog" + ((AlertDialog) dialog).getButton(which).getClass()
                                    .getSimpleName(),
                            (JSONObject) null);
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            startActivity(new Intent(this, ListActivity.class).putExtra(ListActivity.KEY_TYPE, id));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        Toast.makeText(this, "onProgressChanged: " + progress + ", " + fromUser, Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onProgressChanged_TestActivity" + seekBar.getClass().getSimpleName(), (JSONObject) null);
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
        Toast.makeText(this, "onStartTrackingTouch", Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onStartTrackingTouch_TestActivity" + seekBar.getClass().getSimpleName(), (JSONObject) null);
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
        Toast.makeText(this, "onStopTrackingTouch", Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onStopTrackingTouch_TestActivity" + seekBar.getClass().getSimpleName(), (JSONObject) null);
    }

    @Override
    public void onRatingChanged(final RatingBar ratingBar, final float rating, final boolean fromUser) {
        Toast.makeText(this, "onRatingChanged: " + rating + ", " + fromUser, Toast.LENGTH_SHORT).show();
        AppLog.onEventV3("onRatingChanged_TestActivity" + ratingBar.getClass().getSimpleName(), (JSONObject) null);
    }
}
