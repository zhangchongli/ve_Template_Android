package com.volcengine.plugin1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Plugin1Provider extends ContentProvider {
    @Override
    public boolean onCreate() {
        Log.i("mira-demo", "Plugin1Provider onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i("mira-demo", "Plugin1Provider query");
        Toast.makeText(getContext(), "插件1Provider被query", Toast.LENGTH_SHORT).show();
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i("mira-demo", "Plugin1Provider getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i("mira-demo", "Plugin1Provider insert");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("mira-demo", "Plugin1Provider delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i("mira-demo", "Plugin1Provider update");
        return 0;
    }
}
