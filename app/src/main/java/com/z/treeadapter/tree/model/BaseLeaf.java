package com.z.treeadapter.tree.model;

import com.z.treeadapter.application.BaseModel;

/**
 * BaseLeaf
 *
 * @author KID
 * @date 2020/4/22.
 */
public class BaseLeaf extends BaseModel {
    /**
     * 节点是否被选中
     */
    private boolean selected = false;

    /**
     * 构造函数
     */
    public BaseLeaf() {
    }

    /**
     * 获取节点是否被选中
     *
     * @return 是否被选中
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * 设置节点是否被选中
     *
     * @param selected 是否被选中
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}