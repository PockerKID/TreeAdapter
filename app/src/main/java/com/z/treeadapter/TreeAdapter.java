package com.z.treeadapter;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.z.db.greendao.entities.Department;
import com.z.db.greendao.entities.User;
import com.z.treeadapter.application.AppHelper;
import com.z.treeadapter.model.DepartmentNode;
import com.z.treeadapter.model.UserLeaf;
import com.z.treeadapter.tree.adapter.BaseRecyclerviewTreeAdapter;
import com.z.treeadapter.tree.holder.BaseRecyclerviewHolder;
import com.z.treeadapter.tree.model.TreeNode;
import com.z.treeadapter.utils.ResHelper;

import java.util.List;

/**
 * TreeAdapter
 *
 * @author KID
 * @date 2020/4/22.
 */
public class TreeAdapter extends BaseRecyclerviewTreeAdapter<DepartmentNode, UserLeaf> {
    /**
     * 上下文
     */
    private Context context;

    /**
     * 布局初始化
     */
    private LayoutInflater layoutInflater;

    /**
     * 是否是选中模式
     */
    private boolean selectMode;

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param treeNodes 树节点列表
     */
    public TreeAdapter(Context context, List<TreeNode<DepartmentNode, UserLeaf>> treeNodes) {
        this(context, treeNodes, false);
    }

    /**
     * 构造函数
     *
     * @param context    上下文
     * @param treeNodes  树节点列表
     * @param selectMode 是否是选择模式
     */
    public TreeAdapter(Context context, List<TreeNode<DepartmentNode, UserLeaf>> treeNodes, boolean selectMode) {
        super(treeNodes);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        setNodeDeviation(16);
        setSelectMode(selectMode);
    }

    @Override
    public View getPaddingView(BaseRecyclerviewHolder holder) {
        if (holder instanceof NodeHolder) {
            return ((NodeHolder) holder).clRoot;
        } else if (holder instanceof LeafNormalHolder) {
            return ((LeafNormalHolder) holder).clRoot;
        }
        return null;
    }

    @Override
    public BaseRecyclerviewHolder getNodeHolder(ViewGroup parent, int viewType) {
        return new NodeHolder(layoutInflater.inflate(R.layout.tree_department_item, parent, false), viewType);
    }

    @Override
    public BaseRecyclerviewHolder getLeafHolder(ViewGroup parent, int viewType) {
        return new LeafNormalHolder(layoutInflater.inflate(R.layout.tree_user_item, parent, false), viewType);
    }

    /**
     * 设置是否是编辑模式
     *
     * @param selectMode 是否是编辑模式
     */
    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
        notifyDataSetChanged();
    }

    /**
     * 重置选中状态
     *
     * @param isSelected 是否选中
     */
    public void resetSelectedState(boolean isSelected) {
        for (int i = 0; i < treeNodeList.size(); i++) {
            final UserLeaf leaf = treeNodeList.get(i).getLeaf();
            if (leaf == null) {
                continue;
            }
            leaf.setSelected(isSelected);
        }
    }

    /**
     * 获取选中的叶子列表
     *
     * @param accountId  账户ID
     * @param isSelected 是否选中
     */
    public void changeSelectedState(String accountId, boolean isSelected) {
        for (int i = 0; i < treeNodeList.size(); i++) {
            final UserLeaf leaf = treeNodeList.get(i).getLeaf();
            if (leaf == null) {
                continue;
            }
            if (leaf.getUser().getUserId().equals(accountId)) {
                leaf.setSelected(isSelected);
            }
        }
    }

    /**
     * 字节点布局
     */
    private class NodeHolder extends BaseRecyclerviewHolder {
        private ConstraintLayout clRoot;
        private TextView tvDepartment;
        private ImageView ivArrow;

        public NodeHolder(View itemView, int viewType) {
            super(itemView, viewType);
            clRoot = itemView.findViewById(R.id.cl_department_root);
            tvDepartment = itemView.findViewById(R.id.tv_department);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
        }

        @Override
        public void bindItem(BaseRecyclerviewHolder holder, int position) {
            holder.itemView.setBackgroundColor(ResHelper.getDefaultBackgroundColor(context, position));
            TreeNode<DepartmentNode, UserLeaf> node = treeNodeList.get(position);
            if (node != null) {
                final DepartmentNode currentNode = node.getCurrentNode();
                Department department = currentNode.getDepartment();
                if (department != null) {
                    ResHelper.setText(tvDepartment, department.getName());
                }
                if (currentNode.isExpand()) {
                    ivArrow.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    ivArrow.setImageResource(R.drawable.ic_arrow_down);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentNode.isExpand()) {
                            close(node);
                        } else {
                            addNode(AppHelper.getInstance().getChildNodeList(node, false), node);
                        }
                        if (iTreeNodeClickListener != null) {
                            iTreeNodeClickListener.onNodeClick(position, currentNode);
                        }
                    }
                });
            }
        }
    }

    /**
     * 字节点布局
     */
    private class LeafNormalHolder extends BaseRecyclerviewHolder {
        private ConstraintLayout clRoot;
        private TextView tvUser;
        private CheckBox cbSelect;

        public LeafNormalHolder(View itemView, int viewType) {
            super(itemView, viewType);
            clRoot = itemView.findViewById(R.id.cl_user_root);
            tvUser = itemView.findViewById(R.id.tv_user);
            cbSelect = itemView.findViewById(R.id.cb_user_select);
        }

        @Override
        public void bindItem(BaseRecyclerviewHolder holder, int position) {
            holder.itemView.setBackgroundColor(ResHelper.getDefaultBackgroundColor(context, position));
            final UserLeaf userLeaf = treeNodeList.get(position).getLeaf();
            if (userLeaf == null) {
                return;
            }
            final User user = userLeaf.getUser();
            if (user == null) {
                return;
            }
            tvUser.setText(user.getName());
            if (selectMode) {
                ResHelper.changeVisibility(cbSelect, View.VISIBLE);
                cbSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        if (!userLeaf.isSelected()) {
                            userLeaf.setSelected(true);
                        }
                    } else {
                        if (userLeaf.isSelected()) {
                            userLeaf.setSelected(false);
                        }
                    }
                    if (iTreeNodeClickListener != null) {
                        iTreeNodeClickListener.onLeafSelect(position, userLeaf);
                    }
                });

                clRoot.setOnClickListener(v -> {
                    cbSelect.setChecked(!cbSelect.isChecked());
                });
                cbSelect.setChecked(userLeaf.isSelected());
            } else {
                ResHelper.changeVisibility(cbSelect, View.GONE);
                clRoot.setOnClickListener(v -> {
                    if (iTreeNodeClickListener != null) {
                        iTreeNodeClickListener.onLeafClick(position, userLeaf);
                    }
                });
            }
        }
    }
}