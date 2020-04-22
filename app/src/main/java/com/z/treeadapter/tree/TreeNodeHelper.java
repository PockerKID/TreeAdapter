package com.z.treeadapter.tree;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.z.treeadapter.application.AppHelper;

/**
 * TreeNodeHelper
 *
 * @author KID
 * @date 2020/4/22.
 */
public class TreeNodeHelper {
    /**
     * dp to px
     *
     * @param dp dp
     * @return px
     */
    public static int dp2px(float dp) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = AppHelper.getInstance().getWindowManager();
        wm.getDefaultDisplay().getMetrics(dm);
        return (int) (dp * dm.density + 0.5f);
    }

    /**
     * 获取Index
     *
     * @param holder   ViewHolder
     * @param position Position
     * @return Index
     */
    public static int getPosition(RecyclerView.ViewHolder holder, int position) {
        final int index;
        if (holder == null) {
            index = position;
        } else {
            if (holder.getAdapterPosition() == -1) {
                index = position;
            } else {
                index = holder.getAdapterPosition();
            }
        }
        return index;
    }
}
