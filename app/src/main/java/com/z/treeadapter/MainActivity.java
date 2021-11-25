package com.z.treeadapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.z.treeadapter.application.AppHelper;
import com.z.treeadapter.application.BaseActivity;
import com.z.treeadapter.mode.DepartmentNode;
import com.z.treeadapter.mode.UserLeaf;
import com.z.treeadapter.tree.listener.ITreeNodeClickListener;
import com.z.treeadapter.tree.model.TreeNode;
import com.z.treeadapter.utils.ToastHelper;

import java.util.List;

/**
 * @author KID
 * @date 2020/4/22.
 */
public class MainActivity extends BaseActivity {

    /**
     * TAG
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * RecyclerView
     */
    private RecyclerView recyclerView;

    /**
     * TreeAdapter
     */
    private TreeAdapter treeAdapter;

    /**
     * 是否是选中模式
     */
    private boolean selectMode;

    /**
     * 点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int id = v.getId();
            switch (id) {
                case R.id.fab:
                    selectMode = !selectMode;
                    changeTreeStyle(selectMode);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 点击事件
     */
    private ITreeNodeClickListener<DepartmentNode, UserLeaf> iTreeNodeClickListener = new ITreeNodeClickListener<DepartmentNode, UserLeaf>() {
        @Override
        public void onNodeClick(int position, DepartmentNode node) {
            ToastHelper.getInstance().showShortToast("Clicked-->" + node.getDepartment().getName());
        }

        @Override
        public void onLeafClick(int position, UserLeaf leaf) {
            ToastHelper.getInstance().showShortToast("Clicked-->" + leaf.getUser().getName());
        }

        @Override
        public void onLeafSelect(int position, UserLeaf leaf) {
            if(leaf.isSelected()) {
                ToastHelper.getInstance().showShortToast("Selected-->" + leaf.getUser().getName());
            }else {
                ToastHelper.getInstance().showShortToast("Cancel select-->" + leaf.getUser().getName());
            }
        }
    };

    @Override
    protected void initView() {
        super.initView();
        recyclerView = findViewById(R.id.rv_tree);
    }

    @Override
    protected void initParameters() {
        //已获取存储权限
        AppHelper.getInstance().init(activity);

        //加载树节点
        showTree(AppHelper.getInstance().getLocalNodeList());
    }

    @Override
    protected void onDestroy() {
        AppHelper.getInstance().release();
        super.onDestroy();
    }

    @Override
    protected void changeListener(boolean enable) {
        if (enable) {
            fab.setOnClickListener(onClickListener);
        } else {
            fab.setOnClickListener(null);
        }
    }

    /**
     * 显示树
     *
     * @param treeNodeList 节点列表
     */
    private void showTree(List<TreeNode<DepartmentNode, UserLeaf>> treeNodeList) {
        if (treeAdapter == null) {
            treeAdapter = new TreeAdapter(activity, treeNodeList, false);
            treeAdapter.setTreeNodeClickListener(iTreeNodeClickListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(treeAdapter);
        } else {
            treeAdapter.refreshList(treeNodeList, true);
        }
    }

    /**
     * 更改样式
     *
     * @param selectMode 选中模式
     */
    private void changeTreeStyle(boolean selectMode) {
        if (treeAdapter != null) {
            treeAdapter.setSelectMode(selectMode);
        }
    }
}
