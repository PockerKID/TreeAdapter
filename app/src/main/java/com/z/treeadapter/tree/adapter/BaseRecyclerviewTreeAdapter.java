package com.z.treeadapter.tree.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.z.treeadapter.tree.TreeNodeHelper;
import com.z.treeadapter.tree.holder.BaseRecyclerviewHolder;
import com.z.treeadapter.tree.listener.ITreeNodeClickListener;
import com.z.treeadapter.tree.model.BaseLeaf;
import com.z.treeadapter.tree.model.BaseNode;
import com.z.treeadapter.tree.model.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRecyclerviewTreeAdapter
 *
 * @author KID
 * @date 2020/4/22.
 */
public abstract class BaseRecyclerviewTreeAdapter<T extends BaseNode, K extends BaseLeaf> extends RecyclerView.Adapter<BaseRecyclerviewHolder> {
    /**
     * TAG
     */
    private final String TAG = BaseRecyclerviewTreeAdapter.class.getSimpleName();

    /**
     * 树节点列表
     */
    protected List<TreeNode<T, K>> treeNodeList;

    /**
     * 叶子
     */
    protected final int LEAF = 0;

    /**
     * 节点
     */
    protected final int NODE = 1;

    /**
     * 默认节点缩进
     */
    protected int defaultNodeDeviation = 10;

    /**
     * 点击事件监听
     */
    protected ITreeNodeClickListener<T, K> iTreeNodeClickListener;

    /**
     * 构造函数
     *
     * @param treeNodeList 树节点列表
     */
    public BaseRecyclerviewTreeAdapter(List<TreeNode<T, K>> treeNodeList) {
        refreshList(treeNodeList, false);
    }

    /**
     * 刷新树节点列表
     *
     * @param treeNodeList 树节点列表
     * @param notify       是否通知
     */
    public void refreshList(List<TreeNode<T, K>> treeNodeList, boolean notify) {
        if (this.treeNodeList == null) {
            this.treeNodeList = new ArrayList<>();
        } else {
            this.treeNodeList.clear();
        }
        this.treeNodeList.addAll(treeNodeList);
        if (notify) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return treeNodeList == null ? 0 : treeNodeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (treeNodeList.get(position).isNode()) {
            return NODE;
        } else if (treeNodeList.get(position).isLeaf()) {
            return LEAF;
        }
        return NODE;
    }

    @NonNull
    @Override
    public BaseRecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseRecyclerviewHolder baseRecyclerviewHolder;
        switch (viewType) {
            default:
            case NODE:
                baseRecyclerviewHolder = getNodeHolder(parent, viewType);
                break;
            case LEAF:
                baseRecyclerviewHolder = getLeafHolder(parent, viewType);
                break;
        }
        return baseRecyclerviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerviewHolder holder, int position) {
        if (treeNodeList == null) {
            return;
        }
        //有时holder.getAdapterPosition()==-1
        final int index = TreeNodeHelper.getPosition(holder, position);
        if (index == RecyclerView.NO_POSITION) {
            return;
        }
        final TreeNode<T, K> node = treeNodeList.get(index);
        final View tempView = getPaddingView(holder);
        final View view;
        if (tempView == null) {
            view = holder.itemView;
        } else {
            view = tempView;
        }
        view.setPadding(
                node.getDepth() * TreeNodeHelper.dp2px(defaultNodeDeviation),
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom()
        );
        holder.bindItem(holder, index);
    }


    /**
     * 添加子节点
     *
     * @param newNode 子节点
     * @param pNode   父节点
     */
    public void addNode(TreeNode<T, K> newNode, TreeNode<T, K> pNode) {
        if (pNode.isLeaf()) {
            //父节点不能是叶子
            return;
        }
        for (int i = 0; i < treeNodeList.size(); i++) {
            if (treeNodeList.get(i) == pNode) {
                treeNodeList.add(i + 1, newNode);
                break;
            }
        }
        pNode.getCurrentNode().open();
        notifyDataSetChanged();
    }

    /**
     * 添加子节点
     *
     * @param nodes 子节点列表
     * @param pNode 父节点
     */
    public void addNode(List<TreeNode<T, K>> nodes, TreeNode<T, K> pNode) {
        if (pNode.isLeaf()) {
            //父节点不能是叶子
            return;
        }
        for (int i = 0; i < treeNodeList.size(); i++) {
            if (treeNodeList.get(i) == pNode) {
                treeNodeList.addAll(i + 1, nodes);
                break;
            }
        }
        pNode.getCurrentNode().open();
        notifyDataSetChanged();
    }

    /**
     * 关闭节点
     *
     * @param node 节点
     */
    public void close(TreeNode<T, K> node) {
        closeNode(node);
        node.getCurrentNode().close();
        notifyDataSetChanged();
    }

    /**
     * 递归关闭节点
     *
     * @param node 节点
     */
    private void closeNode(TreeNode<T, K> node) {
        if (node.isLeaf()) {
            //节点不能是叶子
            return;
        }

        if (node.getCurrentNode().isExpand()) {
            for (int i = 0; i < treeNodeList.size(); i++) {
                if (treeNodeList.get(i).getParentNode() == node) {
                    if (treeNodeList.get(i).isLeaf()) {
                        treeNodeList.remove(i);
                        i--;
                    } else {
                        closeNode(treeNodeList.get(i));
                        treeNodeList.remove(i);
                        i--;
                    }
                }
            }
        }
    }

    /**
     * 获取选中的叶子列表
     *
     * @return 选中的叶子列表
     */
    public List<K> getSelectedLeaf() {
        final List<K> selectedList = new ArrayList<>();
        for (int i = 0; i < treeNodeList.size(); i++) {
            final K leaf = treeNodeList.get(i).getLeaf();
            if (leaf == null) {
                continue;
            }
            if (leaf.isSelected()) {
                selectedList.add(leaf);
            }
        }
        return selectedList;
    }

    /**
     * 设置默认节点缩进值
     *
     * @param nodeDeviation 默认节点缩进值
     */
    public void setNodeDeviation(int nodeDeviation) {
        this.defaultNodeDeviation = nodeDeviation;
    }

    /**
     * 设置点击监听
     *
     * @param iTreeNodeClickListener 点击监听
     */
    public void setTreeNodeClickListener(ITreeNodeClickListener<T, K> iTreeNodeClickListener) {
        this.iTreeNodeClickListener = iTreeNodeClickListener;
    }

    /**
     * 获取要设置Padding的View
     *
     * @param holder BaseRecyclerviewHolder
     * @return 要设置Padding的View
     */
    public abstract View getPaddingView(BaseRecyclerviewHolder holder);

    /**
     * 节点布局
     *
     * @param parent   ViewGroup
     * @param viewType viewType
     * @return 节点布局
     */
    public abstract BaseRecyclerviewHolder getNodeHolder(ViewGroup parent, int viewType);

    /**
     * 叶子布局
     *
     * @param parent   ViewGroup
     * @param viewType viewType
     * @return 叶子布局
     */
    public abstract BaseRecyclerviewHolder getLeafHolder(ViewGroup parent, int viewType);
}