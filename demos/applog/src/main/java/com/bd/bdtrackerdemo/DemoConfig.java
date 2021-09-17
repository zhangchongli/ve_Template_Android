package com.bd.bdtrackerdemo;

import android.content.Context;
import android.content.SharedPreferences;

public class DemoConfig {
    public static final String SP_TEST_CONFIG = "test_config";
    private static final String KEY_TEST_ENCRYPT_COMPRESS = "test_encrypt_compress";
    private static final String KEY_TEST_AUTO_ACTIVATE = "test_auto_activate";
    private static final String KEY_TEST_NEW_USER_MODE = "test_new_user_mode";

    public static void updateEncryptAndCompressSp(Context context, boolean isChecked) {
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = context.getSharedPreferences(SP_TEST_CONFIG, Context.MODE_PRIVATE);
            sp.edit().putBoolean(KEY_TEST_ENCRYPT_COMPRESS, isChecked).apply();
        }
    }

    public static boolean getEncryptAndCompressSp(Context context) {
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = context.getSharedPreferences(SP_TEST_CONFIG, Context.MODE_PRIVATE);
            return sp.getBoolean(KEY_TEST_ENCRYPT_COMPRESS, false);
        }
        return true;
    }

    public static void updateNewUserModeSp(Context context, boolean isChecked) {
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = context.getSharedPreferences(SP_TEST_CONFIG, Context.MODE_PRIVATE);
            sp.edit().putBoolean(KEY_TEST_NEW_USER_MODE, isChecked).apply();
        }
    }

    public static boolean getNewUserModeSp(Context context) {
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = context.getSharedPreferences(SP_TEST_CONFIG, Context.MODE_PRIVATE);
            return sp.getBoolean(KEY_TEST_NEW_USER_MODE, false);
        }
        return true;
    }

    public static void updateAutoActivateSp(Context context, boolean isChecked) {
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = context.getSharedPreferences(SP_TEST_CONFIG, Context.MODE_PRIVATE);
            sp.edit().putBoolean(KEY_TEST_AUTO_ACTIVATE, isChecked).apply();
        }
    }

    public static boolean getAutoActivateSp(Context context) {
        if (BuildConfig.DEBUG) {
            SharedPreferences sp = context.getSharedPreferences(SP_TEST_CONFIG, Context.MODE_PRIVATE);
            return sp.getBoolean(KEY_TEST_AUTO_ACTIVATE, true);
        }
        return true;
    }
}
