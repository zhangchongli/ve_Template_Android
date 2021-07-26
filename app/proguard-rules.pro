# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-ignorewarnings

# androix start
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**
# androix end

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 枚举类混淆规则
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# hotfix防止代码被内联
-dontoptimize

# mira start
-ignorewarnings
-keep class com.facebook.infer.annotation.Functional {}
-keep class javax.annotation.CheckReturnValue {}
-keep class androidx.annotation.DrawableRes {}
-keep class java.lang.Synthetic {}
-keep class javax.annotation.Nonnull {}
-keep class com.volcengine.mars.ServiceManager {
	void resisterService(java.lang.Class,java.lang.Object);
}
-keep class com.facebook.infer.annotation.ThreadSafe {}
-keep class javax.annotation.concurrent.GuardedBy {}
-keep class javax.annotation.concurrent.Immutable {}
-keep class androidx.annotation.Nullable {}
-keep class androidx.core.util.Pools$SynchronizedPool {
	<init>(int);
	java.lang.Object acquire();
	boolean release(java.lang.Object);
}
-keep class javax.annotation.Nullable {}
-keep class androidx.core.util.Pools {}
-keep class com.volcengine.mars.IPlugin1Service {
	androidx.fragment.app.Fragment getFragment();
}
-keep class androidx.annotation.NonNull {}
-keep class com.facebook.infer.annotation.ReturnsOwnership {}
-keep class androidx.savedstate.SavedStateRegistryOwner {
	androidx.savedstate.SavedStateRegistry getSavedStateRegistry();
}
-keep class androidx.lifecycle.ViewModelStoreOwner {
	androidx.lifecycle.ViewModelStore getViewModelStore();
}
-keep class androidx.lifecycle.LifecycleOwner {
	androidx.lifecycle.Lifecycle getLifecycle();
}
-keep class javax.annotation.concurrent.ThreadSafe {}
-keep class androidx.annotation.ColorInt {}
-keep class javax.annotation.concurrent.NotThreadSafe {}
-keep class androidx.appcompat.app.ActionBarDrawerToggle$DelegateProvider {
	androidx.appcompat.app.ActionBarDrawerToggle$Delegate getDrawerToggleDelegate();
}
-keep class androidx.core.app.ActivityCompat$OnRequestPermissionsResultCallback {
	void onRequestPermissionsResult(int,java.lang.String[],int[]);
}
-keep class androidx.core.app.ActivityCompat$RequestPermissionsRequestCodeValidator {
	void validateRequestPermissionsRequestCode(int);
}
-keep class androidx.fragment.app.FragmentManager {
	androidx.fragment.app.FragmentTransaction beginTransaction();
}
-keep class androidx.core.app.TaskStackBuilder$SupportParentable {
	android.content.Intent getSupportParentActivityIntent();
}
-keep class androidx.core.view.KeyEventDispatcher$Component {
	boolean superDispatchKeyEvent(android.view.KeyEvent);
}
-keep class com.volcengine.mars.HostUtils {
	void showSources(android.content.Context);
}
-keep class androidx.appcompat.app.AppCompatActivity {
	<init>();
	android.view.View findViewById(int);
	void onCreate(android.os.Bundle);
	void setContentView(int);
}
-keep class com.volcengine.mars.ServiceManager {
	java.lang.Object getService(java.lang.Class);
}
-keep interface com.bytedance.morpheus.core.MorpheusStateListener{*;}
-keep class androidx.activity.OnBackPressedDispatcherOwner {
	androidx.activity.OnBackPressedDispatcher getOnBackPressedDispatcher();
}
-keep class androidx.appcompat.app.AppCompatCallback {
	void onSupportActionModeFinished(androidx.appcompat.view.ActionMode);
	void onSupportActionModeStarted(androidx.appcompat.view.ActionMode);
	androidx.appcompat.view.ActionMode onWindowStartingSupportActionMode(androidx.appcompat.view.ActionMode$Callback);
}
-keep class androidx.fragment.app.FragmentTransaction {
	androidx.fragment.app.FragmentTransaction add(int,androidx.fragment.app.Fragment);
	int commit();
	androidx.fragment.app.FragmentTransaction show(androidx.fragment.app.Fragment);
}
-keep class androidx.fragment.app.FragmentActivity {
	androidx.fragment.app.FragmentManager getSupportFragmentManager();
}

# 插件通过spi向宿主暴露的类，需要keep
-keep class androidx.fragment.app.Fragment {
    *;
}
-keep class androidx.constraintlayout.widget.ConstraintLayout {
	<init>(...);
}
-keep class kotlin.Metadata {
	int[] bv();
	java.lang.String[] d1();
	java.lang.String[] d2();
	int k();
	int[] mv();
}
# mira end