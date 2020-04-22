package com.z.treeadapter.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.z.treeadapter.R;

import java.util.UUID;

/**
 * ResHelper
 *
 * @author KID
 * @date 2020/4/22.
 */
public class ResHelper {

    /**
     * 获取默认背景颜色
     *
     * @param context 上下文
     * @param index   位置
     * @return 颜色
     */
    public static int getDefaultBackgroundColor(Context context, int index) {
        int color;
        if (index % 2 == 0) {
            color = getColor(context, R.color.default_background_main_color);
        } else {
            color = getColor(context, R.color.default_background_second_color);
        }
        return color;
    }

    /**
     * 获取颜色
     *
     * @param context Context
     * @param colorId 颜色ID
     * @return 颜色
     */
    public static int getColor(Context context, int colorId) {
        int color;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = context.getColor(colorId);
        } else {
            color = context.getResources().getColor(colorId);
        }
        return color;
    }

    /**
     * 设置文字
     *
     * @param textView 文本框
     * @param text     文字
     */
    public static void setText(TextView textView, String text) {
        if (textView == null) {
            return;
        }
        textView.setText(text);
    }

    /**
     * 设置文字
     *
     * @param editText 输入框
     * @param text     文字
     */
    public static void setText(EditText editText, String text) {
        if (editText == null) {
            return;
        }
        editText.setText(text);
        editText.setSelection(editText.getText().length());
    }

    /**
     * 更改可见性
     *
     * @param view       布局
     * @param visibility 可见性
     */
    public static void changeVisibility(View view, int visibility) {
        if (view == null) {
            return;
        }
        view.setVisibility(visibility);
    }

    /**
     * @return UUID
     */
    public static String getGuid() {
        return UUID.randomUUID().toString();
    }
}
