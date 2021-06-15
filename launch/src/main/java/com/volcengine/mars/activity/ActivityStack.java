package com.volcengine.mars.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import java.util.LinkedList;

/**
 * created by luoqiaoyou on 2020/4/12.
 */
public final class ActivityStack {

    public static class ActivityStackLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            sActivityStack.remove(activity);
            sActivityStack.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityStack.remove(activity);
        }
    }

    /**
     * Activity栈
     */
    private static LinkedList<Activity> sActivityStack = new LinkedList<>();

    /**
     * 初始化
     *
     * @param application 当前的Application对象
     */
    public static void init(Application application) {
        init(application, new ActivityStackLifecycleCallbacks());
    }

    public static void init(Application application, ActivityStackLifecycleCallbacks callbacks) {
        application.registerActivityLifecycleCallbacks(callbacks);
    }

    /**
     * 得到指定activity前一个active activity的实例
     *
     * @param curActivity 当前activity
     * @return 可能为null
     */
    public static Activity getPreviousActivity(Activity curActivity) {
        final LinkedList<Activity> activities = sActivityStack;
        int index = activities.size() - 1;
        boolean findCurActivity = false;
        while (index >= 0) {
            if (findCurActivity) {
                Activity preActiveActivity = activities.get(index);
                if (preActiveActivity != null && !preActiveActivity.isFinishing()) {
                    return preActiveActivity;
                }
            } else if (activities.get(index) == curActivity) {
                findCurActivity = true;
            }
            index--;
        }

        return null;
    }

    /**
     * 获得前一个Activity
     */
    public static synchronized Activity getPreviousActivity() {
        return sActivityStack.size() >= 2 ? sActivityStack.get(sActivityStack.size() - 2) : null;
    }

    /**
     * 获得栈顶的Activity
     */
    public static Activity getTopActivity() {
        return sActivityStack.isEmpty() ? null : sActivityStack.getLast();
    }

    /**
     * 获得Activity栈
     */
    public static synchronized Activity[] getActivityStack() {
        Activity[] activities = new Activity[sActivityStack.size()];
        return sActivityStack.toArray(activities);
    }

    /**
     * 获得栈顶可用的的Activity
     */
    public static synchronized Activity getValidTopActivity() {
        Activity activity = getTopActivity();
        if (activity != null && activity.isFinishing()) {
            sActivityStack.removeLast();
            activity = getValidTopActivity();
        }
        return activity;
    }

    public static synchronized Activity getValidSecondTopActivity() {
        return getPreviousActivity(getValidTopActivity());
    }

    public static synchronized boolean isValidTopActivity(Activity activity) {
        if (activity == null) {
            return false;
        }

        return activity.equals(getValidTopActivity());
    }
}

