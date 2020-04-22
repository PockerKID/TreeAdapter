package com.z.treeadapter.application;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.z.treeadapter.R;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * AppActivity
 *
 * @author KID
 * @date 2020/4/22.
 */
public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    /**
     * TAG
     */
    private static final String TAG = BaseActivity.class.getSimpleName();

    /**
     * 权限请求码
     */
    protected static final int PERMISSION_REQUEST_CODE = 1024;

    /**
     * 设置
     */
    protected Configuration newConfig = null;

    /**
     * 权限列表
     */
    protected static String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    /**
     * Activity
     */
    protected Activity activity;

    /**
     * 按钮
     */
    protected FloatingActionButton fab;

    /**
     * 创建时检查权限
     */
    protected boolean checkPermissionOnCreate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        newConfig = this.getResources().getConfiguration();
        setView();
        if (checkPermissionOnCreate) {
            //检查权限
            requestNormalPermissions(activity, permissions);
        }
    }

    @Override
    protected void onPause() {
        changeListener(false);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeListener(true);
    }

    /**
     * 更改监听
     *
     * @param enable 是否启用
     */
    protected void changeListener(boolean enable) {

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.newConfig = newConfig;
    }

    /**
     * 加载布局
     */
    protected void setView() {
        int currentLayoutId = getPortraitLayoutId();
        if (newConfig.orientation == 1) {
            currentLayoutId = getLandscapeLayoutId();
        }

        this.setContentView(currentLayoutId);
        this.initView();
    }

    /**
     * @return 纵向布局ID
     */
    protected int getPortraitLayoutId() {
        return R.layout.base_activity;
    }

    /**
     * @return 横向布局ID
     */
    protected int getLandscapeLayoutId() {
        return R.layout.base_activity;
    }

    /**
     * 初始化布局
     */
    protected void initView() {
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fab = findViewById(R.id.fab);
    }

    /**
     * 初始化参数
     */
    protected void initParameters() {

    }

    /**
     * 发起一般权限请求
     *
     * @param activity    Activity
     * @param permissions 权限组
     */
    protected void requestNormalPermissions(Activity activity, final String[] permissions) {
        //判断有没有权限
        if (EasyPermissions.hasPermissions(activity, permissions)) {
            // 如果有权限了
            initParameters();
        } else {
            // 如果没有权限,就去申请权限
            // this: 上下文
            // Dialog显示的正文
            // PERMISSION_REQUEST_CODE 请求码, 用于回调的时候判断是哪次申请
            // perms 要申请的权限
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(activity, PERMISSION_REQUEST_CODE, permissions)
                            .setRationale(getString(R.string.permission_request_common_explain))
                            .setPositiveButtonText(R.string.msg_dialog_confirm)
                            .setNegativeButtonText(R.string.msg_dialog_cancel)
                            .build()
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }
        requestNormalPermissions(activity, permissions);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }
        if (perms.isEmpty()) {
            requestNormalPermissions(activity, permissions);
        } else {
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this)
                        .setRationale(
                                getString(R.string.permissions_denied)
                                        + getString(R.string.permission_manually_request)
                        )
                        .setPositiveButton(getString(R.string.permission_authorization_str))
                        .setNegativeButton(getString(R.string.msg_dialog_cancel))
                        .build()
                        .show();
            } else {
                requestNormalPermissions(activity, perms.toArray(new String[perms.size()]));
            }
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        requestNormalPermissions(activity, permissions);
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        exitActivity();
    }

    /**
     * 获取是否在启动是检查权限
     *
     * @return 是否在启动是检查权限
     */
    public boolean isCheckPermissionOnCreate() {
        return checkPermissionOnCreate;
    }

    /**
     * 设置是否在启动是检查权限(需手动检测)
     *
     * @param checkPermissionOnCreate 是否在启动是检查权限
     */
    public void setCheckPermissionOnCreate(boolean checkPermissionOnCreate) {
        this.checkPermissionOnCreate = checkPermissionOnCreate;
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE:
                requestNormalPermissions(activity, permissions);
                break;
            default:
                break;
        }
    }

    /**
     * 退出
     */
    protected void exitActivity() {
        finish();
    }
}
