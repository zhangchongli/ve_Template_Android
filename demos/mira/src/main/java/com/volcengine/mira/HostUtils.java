package com.volcengine.mira;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

public class HostUtils {

    public static void showSources(Context context) {
        Toast.makeText(context, "我是宿主代码", Toast.LENGTH_LONG).show();
    }

    public static void saySources(@NonNull Context context) {
        Toast.makeText(context, "我是宿主的代码", Toast.LENGTH_LONG).show();
    }

    /**
     * 反射静态方法
     */
    public static void reflectStaticMethod(String clazzName, String methodName, Class[] parameterTypes, Object[] parameters) {
        if (parameterTypes == null) {
            parameterTypes = new Class[0];
        }
        if (parameters == null) {
            parameters = new Object[0];
        }
        try {
            Class clazz = Class.forName(clazzName);
            Method method = clazz.getMethod(methodName, parameterTypes);
            method.invoke(null, parameters);
        } catch (Throwable e) {
            Log.e("mirademo", "invokeStaticMethod failed", e);
        }
    }

}
