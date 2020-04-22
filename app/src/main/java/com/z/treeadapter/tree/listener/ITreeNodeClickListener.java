package com.z.treeadapter.tree.listener;

import com.z.treeadapter.tree.model.BaseLeaf;
import com.z.treeadapter.tree.model.BaseNode;
import com.z.treeadapter.tree.model.TreeNode;

/**
 * ITreeNodeClickListener
 *
 * @author KID
 * @date 2020/4/22.
 */
public interface ITreeNodeClickListener<T extends BaseNode, K extends BaseLeaf> {

    /**
     * 节点点击事件
     *
     * @param position 位置
     * @param node     节点
     */
    void onNodeClick(int position, T node);

    /**
     * 叶子点击事件
     *
     * @param position 位置
     * @param leaf     节点
     */
    void onLeafClick(int position, K leaf);

    /**
     * 叶子选中事件
     *
     * @param position 位置
     * @param leaf     节点
     */
    void onLeafSelect(int position, K leaf);
}