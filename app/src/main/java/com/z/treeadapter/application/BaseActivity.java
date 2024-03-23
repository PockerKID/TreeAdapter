package com.z.treeadapter.application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;
import com.z.treeadapter.R;
import com.z.treeadapter.contant.PermissionConstants;

import java.util.List;

/**
 * AppActivity
 *
 * @author KID
 * @date 2020/4/22.
 */
public class BaseActivity extends AppCompatActivity {

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
    protected static String[] permissions = PermissionConstants.PERMISSIONS_BELOW_M;

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

    /**
     * 权限
     */
    protected ActivityResultLauncher<Intent> permissionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> checkPermissions());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        newConfig = this.getResources().getConfiguration();
        setView();
        if (checkPermissionOnCreate) {
            //检查权限
            checkPermissions();
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

    public void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions = PermissionConstants.PERMISSIONS_S;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            permissions = PermissionConstants.PERMISSIONS_R;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions = PermissionConstants.PERMISSIONS_Q;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            permissions = PermissionConstants.PERMISSIONS_O;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions = PermissionConstants.PERMISSIONS_M;
        } else {
            permissions = PermissionConstants.PERMISSIONS_BELOW_M;
        }
        if (XXPermissions.isGranted(this, permissions)) {
            onPermissionGranted();
        } else {
            requestPermissions();
        }
    }

    /**
     * 初始化参数
     */
    protected void onPermissionGranted() {

    }

    /**
     * 发起一般权限请求
     */
    protected void requestPermissions() {
        XXPermissions.with(this)
                .permission(permissions)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(@NonNull List<String> list, boolean b) {
                        // 如果有权限了
                        onPermissionGranted();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        exitActivity();
                    }
                });
    }


    /**
     * 显示权限提示框
     */
    public void showPermissionHintDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.permission_manually_request))
                .setPositiveButton(getString(R.string.permission_authorization_str), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        permissionLauncher.launch(intent);
                    }
                })
                .setNegativeButton(getString(R.string.msg_dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitActivity();
                    }
                }).show();
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
            case PermissionConstants.NORMAL_PERMISSION_REQUEST_CODE:
                requestPermissions();
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
