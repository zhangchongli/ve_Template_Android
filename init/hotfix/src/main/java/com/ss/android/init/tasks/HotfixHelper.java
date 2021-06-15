package com.ss.android.init.tasks;

import android.app.Application;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.bytedance.frankie.Frankie;
import com.bytedance.frankie.FrankieConfigExternalAdapter;
import com.volcengine.mars.app.LaunchApplication;

public class HotfixHelper {

    /**
     * app key, you can get it from mars, this key will be used
     * to request the patch interface, and the patch will only
     * be retrieved if the key is correct
     */
    private static final String APP_KEY = "8dbca9ba14de5952f936c7255c98487b";

    /**
     * app secret key, similar to {@link #APP_KEY}
     */
    private static final String APP_SECRET_KEY = "6606a1770c34c3fdee273985769686bf";

    /**
     * device id
     */
    private static final String DEVICE_ID = "7135843788734";

    /**
     * channel description, you can choose specific channel
     * in patch publish service
     */
    private static final String CHANNEL = "local_test";

    private static volatile HotfixHelper sInstance = null;

    public static HotfixHelper getInstance() {
        if (sInstance == null) {
            synchronized (HotfixHelper.class) {
                if (sInstance == null) {
                    sInstance = new HotfixHelper();
                }
            }
        }
        return sInstance;
    }

    public void init(@NonNull Application application) {
        Frankie.getInstance().init(new DemoFrankieConfig(application));
    }

    /**
     * need to create a config class extends {@link FrankieConfigExternalAdapter},
     * and provide some necessary info
     */
    private static final class DemoFrankieConfig extends FrankieConfigExternalAdapter {

        private static final String TAG = "DemoFrankieConfig";

        private Application mApplication;

        public DemoFrankieConfig(@NonNull Application application) {
            this.mApplication = application;
        }

        @NonNull
        @Override
        public String getAppKey() {
            return APP_KEY;
        }

        @NonNull
        @Override
        public String getAppSecretKey() {
            return APP_SECRET_KEY;
        }

        @NonNull
        @Override
        public Application getApplication() {
            return mApplication;
        }

        @Override
        public String getDeviceId() {
            return DEVICE_ID;
        }

        @Override
        public String getChannel() {
            return CHANNEL;
        }

        @Override
        public boolean isMainProcess() {
            return TextUtils.equals(mApplication.getPackageName(), LaunchApplication.getProcessName(mApplication));
        }
    }

}
