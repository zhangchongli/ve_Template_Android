package com.volcengine.mars.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.volcengine.mars.permissions.PermissionsManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode >> 16 & 0xffff) == 0) {
            // forwarding request to permission manager
            PermissionsManager.getInstance().notifyPermissionsChange(this, permissions, grantResults);
        }
    }
}
