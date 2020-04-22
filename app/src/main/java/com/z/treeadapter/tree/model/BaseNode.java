package com.z.treeadapter.tree.model;

import com.z.treeadapter.application.BaseModel;

/**
 * BaseNode
 *
 * @author KID
 * @date 2020/4/22.
 */
public abstract class BaseNode extends BaseModel {

    /**
     * 节点是否展开
     */
    private boolean isExpand = false;

    /**
     * 构造函数
     */
    public BaseNode() {
    }

    /**
     * 关闭节点
     */
    public void close() {
        setExpand(false);
    }

    /**
     * 打开节点
     */
    public void open() {
        setExpand(true);
    }


    /**
     * 获取节点是否展开
     *
     * @return 节点是否展开
     */
    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 设置获取节点是否展开
     *
     * @param expand 获取节点是否展开
     */
    public void setExpand(boolean expand) {
        isExpand = expand;
    }
}