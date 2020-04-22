package com.z.treeadapter.tree.model;

/**
 * TreeNode
 *
 * @author KID
 * @date 2020/4/22.
 */
public class TreeNode<T extends BaseNode, K extends BaseLeaf> {

    /**
     * 当前节点
     */
    private T currentNode;

    /**
     * 叶子(当前节点挂载的非下级节点)
     * 如:
     * A(根节点)
     * |----Aa(叶子)
     * |----Ab(叶子)
     * |----B(子节点)
     * |----|
     * |----|----Ba(叶子)
     * |----|----Bb(叶子)
     */
    private K leaf;

    /**
     * 深度
     */
    private int depth = 0;

    /**
     * 父节点
     */
    private TreeNode<T, K> parentNode;

    /**
     * 构造函数
     *
     * @param currentNode 当前节点
     * @param leaf        叶子
     * @param parentNode  父节点
     */
    public TreeNode(T currentNode, K leaf, TreeNode<T, K> parentNode) {
        this.currentNode = currentNode;
        this.leaf = leaf;
        setParentNode(parentNode);
    }

    /**
     * 获取当前节点
     *
     * @return 当前节点
     */
    public T getCurrentNode() {
        return currentNode;
    }

    /**
     * 设置当前节点
     *
     * @param currentNode 当前节点
     */
    public void setCurrentNode(T currentNode) {
        this.currentNode = currentNode;
    }

    /**
     * 获取叶子
     *
     * @return 叶子
     */
    public K getLeaf() {
        return leaf;
    }

    /**
     * 设置叶子
     *
     * @param leaf 叶子
     */
    public void setLeaf(K leaf) {
        this.leaf = leaf;
    }

    /**
     * 获取父节点
     *
     * @return 父节点
     */
    public TreeNode<T, K> getParentNode() {
        return parentNode;
    }

    /**
     * 设置父节点
     *
     * @param parentNode 父节点
     */
    public void setParentNode(TreeNode<T, K> parentNode) {
        this.parentNode = parentNode;
        if (null == parentNode) {
            depth = 0;
        } else {
            depth = parentNode.getDepth() + 1;
        }
    }

    /**
     * 获取深度
     *
     * @return 深度
     */
    public int getDepth() {
        return depth;
    }

    /**
     * 是否是叶子
     *
     * @return 是否是叶子
     */
    public boolean isLeaf() {
        final boolean result;
        if (isNode()) {
            //是节点
            result = false;
        } else {
            //叶子不为空,则为叶子
            result = leaf != null;
        }
        return result;
    }

    /**
     * 是否是节点
     *
     * @return 是否是节点
     */
    public boolean isNode() {
        return currentNode != null;
    }

    /**
     * 是否是根节点
     *
     * @return 是否是根节点
     */
    public boolean isRoot() {
        return parentNode == null;
    }
}