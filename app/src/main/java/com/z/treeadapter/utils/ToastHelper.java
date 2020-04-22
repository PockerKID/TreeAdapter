package com.z.treeadapter.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * ToastHelper
 *
 * @author KID
 * @date 2020/4/22.
 */
public class ToastHelper {
    /**
     * 单例对象
     */
    private static volatile ToastHelper singleton;

    /**
     * 上下文
     */
    private Context context;

    /**
     * Toast
     */
    private Toast toast;

    /**
     * 构造函数
     */
    private ToastHelper() {
    }

    /**
     * @return 单例对象
     */
    public static ToastHelper getInstance() {
        if (singleton == null) {
            synchronized (ToastHelper.class) {
                if (singleton == null) {
                    singleton = new ToastHelper();
                }
            }
        }
        return singleton;
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * 释放
     */
    public void release() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        this.context = null;
        singleton = null;
    }

    public void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    public void showShortToast(int messageId) {
        String message = context.getString(messageId);
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showLongToast(int messageId) {
        String message = context.getString(messageId);
        showToast(message, Toast.LENGTH_LONG);
    }

    private void showToast(String message, int showTimeType) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, message, showTimeType);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
