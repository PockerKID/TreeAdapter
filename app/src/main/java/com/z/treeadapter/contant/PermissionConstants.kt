package com.z.treeadapter.contant

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions

/**
 * PermissionConstants
 *
 * Date: 2022/10/28/0028
 * Description:
 *
 * @author z
 * @version 1.0.0
 */
object PermissionConstants {

    /**
     * 请求码
     */
    const val NORMAL_PERMISSION_REQUEST_CODE = 1024

    /**
     * 获取读写外部存储权限的字符串
     */
    @JvmStatic
    fun getStoragePermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            arrayOf(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        } else {
            arrayOf(
//                //若使用XXPermission,
//                //则不需要写Manifest.permission.WRITE_EXTERNAL_STORAGE和Manifest.permission.READ_EXTERNAL_STORAGE
//                //写Manifest.permission.MANAGE_EXTERNAL_STORAGE的值,XXPermission在低版本上会自动替换
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "android.permission.MANAGE_EXTERNAL_STORAGE"
            )
        }
    }

    /**
     * 安卓6以下的权限申请
     */
    @JvmField
    val PERMISSIONS_BELOW_M = emptyArray<String>()

    /**
     * 安卓6的权限申请
     */
    @JvmField
    @RequiresApi(Build.VERSION_CODES.M)
    val PERMISSIONS_M = getStoragePermissions()

    /**
     * 安卓8以下的权限申请
     */
    @JvmField
    @RequiresApi(Build.VERSION_CODES.M)
    val PERMISSIONS_BELOW_O = PERMISSIONS_M

    /**
     * 安卓8以上的权限申请
     */
    @JvmField
    @RequiresApi(Build.VERSION_CODES.O)
    val PERMISSIONS_O = PERMISSIONS_BELOW_O

    /**
     * 后台定位权限
     */
    @RequiresApi(Build.VERSION_CODES.Q)
    const val BACKGROUND_LOCATION_PERMISSION = Manifest.permission.ACCESS_BACKGROUND_LOCATION

    /**
     * 安卓10以上的权限申请
     */
    @JvmField
    @RequiresApi(Build.VERSION_CODES.Q)
    val PERMISSIONS_Q = PERMISSIONS_O

    /**
     * 安卓11以上的权限申请
     */
    @JvmField
    @RequiresApi(Build.VERSION_CODES.R)
    val PERMISSIONS_R = PERMISSIONS_Q

    /**
     * 安卓12以上的权限申请
     */
    @JvmField
    @RequiresApi(Build.VERSION_CODES.S)
    val PERMISSIONS_S = PERMISSIONS_R

    /**
     * 请求后台定位权限
     *
     * @param context              上下文
     * @param onPermissionCallback 回调
     */
    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.Q)
    fun requestBackgroundLocationPermission(
        context: Context,
        onPermissionCallback: OnPermissionCallback
    ) {
        XXPermissions.with(context)
            .permission(BACKGROUND_LOCATION_PERMISSION)
            .request(onPermissionCallback)
    }
}