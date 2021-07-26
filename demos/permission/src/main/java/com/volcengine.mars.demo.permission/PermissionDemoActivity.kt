package com.volcengine.mars.demo.permission

import android.R.layout
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckedTextView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.volcengine.mars.permissions.PermissionsManager
import com.volcengine.mars.permissions.PermissionsManager.DefaultDialogBuilder
import com.volcengine.mars.permissions.PermissionsManager.DialogBuilder
import com.volcengine.mars.permissions.PermissionsManager.DialogBuilderProvider
import com.volcengine.mars.permissions.PermissionsResultAction
import java.util.HashMap

class PermissionDemoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "PermissionDemoActivity"
    }

    lateinit var listView: ListView
    var permissions: HashMap<String, String>? = null
    val selectedPermissions = HashSet<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_demo)
        listView = findViewById(R.id.listView)
        listView.adapter =
            ArrayAdapter<String>(
                this,
                layout.simple_list_item_single_choice,
                allPermissions()
            )
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (view is CheckedTextView) {
                    if (view.isChecked) {
                        selectedPermissions.add(view.text.toString())
                    } else {
                        selectedPermissions.remove(view.text.toString())
                    }
                }
            }
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
    }

    /**
     * handle the permission request callbacks
     * */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode shr 16 and 0xffff == 0) {
            // forwarding request to permission manager
            PermissionsManager.getInstance().notifyPermissionsChange(this, permissions, grantResults)
        }
    }

    @Synchronized
    private fun getManifestPermissions(activity: Activity): Array<String> {
        var packageInfo: PackageInfo? = null
        val list = java.util.ArrayList<String>(1)
        try {
            Log.d(TAG, activity.packageName)
            packageInfo = activity.packageManager.getPackageInfo(
                activity.packageName,
                PackageManager.GET_PERMISSIONS
            )
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "A problem occurred when retrieving permissions", e)
        }

        if (packageInfo != null) {
            val permissions = packageInfo.requestedPermissions
            if (permissions != null) {
                for (perm in permissions) {
                    Log.d(TAG, "Manifest contained permission: $perm")
                    list.add(perm)
                }
            }
        }
        return list.toTypedArray()
    }

    private fun  allPermissions(): List<String> {
        val result = ArrayList<String>()
        if (permissions == null) {
            permissions = HashMap()
        }
        result.addAll(getManifestPermissions(this))
        return result
    }

    /**
     * This is a example to explain how to set a custom Dialog when you want to guild user to open permissions manually
     * The user can set PositiveButton and NegativeButton events
     * */
    val dialogBuilderProvider = object : DialogBuilderProvider{
        override fun createBuilder(context: Context?): DialogBuilder {
            val builder = DefaultDialogBuilder(context)
            builder.setTitle("权限申请-This is the title you can set")
                   .setMessage("This you can set such as reason for this permission")
//            builder.setPositiveButton()
//            builder.setNegativeButton()
            return builder
        }
    }

    /**
     * This is a example to explain how to use {@link PermissionsManager#requestPermissionsIfNecessaryForResult()}
     * method to request one and more then one permissions and override two methods in {@link PermissionsResultAction}
     * to handle both successful and failed permission cases as callback
     * Of course the user can still override {@link Activity#onRequestPermissionsResult()} to do it, while this provide
     * an alternative and better way, instead of intruding into the Activity
     * */
    fun onRequest(view: View) {
//        PermissionsManager.setDialogBuilderProvider(dialogBuilderProvider)
        PermissionsManager.getInstance()
            .requestPermissionsIfNecessaryForResult(
                this,
                selectedPermissions.toTypedArray(),
                object : PermissionsResultAction() {
                    override fun onGranted() {
                        Toast.makeText(this@PermissionDemoActivity, "granted", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onDenied(permission: String?) {
                        Toast.makeText(
                            this@PermissionDemoActivity,
                            "denied:$permission",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    /**
     * This is a example to explain how to use {@link PermissionsManager#hasAllPermissions()} method to check if
     * currently obtain these permissions.
     * */
    fun onCheck(view: View) {
        Toast.makeText(
            this,
            "${
                PermissionsManager.getInstance().hasAllPermissions(
                    this,
                    selectedPermissions.toTypedArray()
                )
            }",
            Toast.LENGTH_SHORT
        ).show()
    }
}