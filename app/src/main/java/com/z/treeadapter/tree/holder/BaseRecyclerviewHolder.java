package com.z.treeadapter.tree.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * BaseRecyclerviewHolder
 *
 * @author KID
 * @date 2020/4/22.
 */
public abstract class BaseRecyclerviewHolder extends RecyclerView.ViewHolder {
    /**
     * 构造函数
     *
     * @param itemView ItemView
     */
    public BaseRecyclerviewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * 构造函数
     *
     * @param itemView ItemView
     * @param viewType ViewType
     */
    public BaseRecyclerviewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
    }

    /**
     * 绑定Item
     *
     * @param holder   ViewHolder
     * @param position 位置
     */
    public abstract void bindItem(BaseRecyclerviewHolder holder, int position);
}