package com.volcengine.mars.fragment

import androidx.fragment.app.Fragment
import com.volcengine.mars.permissions.PermissionsManager

/**
 *
 * created by luoqiaoyou on 2020/6/15.
 */
abstract class BaseFragment : Fragment() {

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activity?.let {
            // forwarding request to permission manager
            PermissionsManager.getInstance().notifyPermissionsChange(it, permissions, grantResults)
        }
    }
}