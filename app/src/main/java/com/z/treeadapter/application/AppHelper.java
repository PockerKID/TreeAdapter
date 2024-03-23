package com.z.treeadapter.application;

import android.content.Context;
import android.os.Environment;
import android.view.WindowManager;

import com.github.promeg.pinyinhelper.Pinyin;
import com.z.db.DatabaseManager;
import com.z.db.base.dao.DbType;
import com.z.db.greendao.bll.BllFactory;
import com.z.db.greendao.bll.DepartmentBll;
import com.z.db.greendao.bll.UserBll;
import com.z.db.greendao.entities.Department;
import com.z.db.greendao.entities.User;
import com.z.db.greendao.manager.DbUsage;
import com.z.treeadapter.R;
import com.z.treeadapter.model.DepartmentNode;
import com.z.treeadapter.model.UserLeaf;
import com.z.treeadapter.tree.model.TreeNode;
import com.z.treeadapter.utils.ResHelper;
import com.z.treeadapter.utils.ToastHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * AppHelper
 *
 * @author KID
 * @date 2020/4/22.
 */
public class AppHelper {

    /**
     * 标签
     */
    private static final String TAG = AppHelper.class.getSimpleName();

    /**
     * 单例对象
     */
    private static volatile AppHelper singleton;

    /**
     * 上下文
     */
    private Context context;

    /**
     * 系统窗口管理对象
     */
    private WindowManager windowManager = null;

    /**
     * 数据库
     */
    private BllFactory bllFactory;

    /**
     * 构造函数
     */
    private AppHelper() {
    }

    /**
     * @return 单例对象
     */
    public static AppHelper getInstance() {
        if (singleton == null) {
            synchronized (AppHelper.class) {
                if (singleton == null) {
                    singleton = new AppHelper();
                }
            }
        }
        return singleton;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        this.context = context;
        ToastHelper.getInstance().init(context);
        initWorkSpace();

        //生成模拟数据
        generateData();
    }

    /**
     * 释放
     */
    public void release() {
        if (bllFactory != null) {
            bllFactory.close();
            bllFactory = null;
        }
        ToastHelper.getInstance().release();
        windowManager = null;
        this.context = null;
        singleton = null;
    }

    /**
     * 初始化工作空间
     */
    private void initWorkSpace() {
        File dir;
        try {
            dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "TreeAdapter");
        } catch (Exception e) {
            dir = new File(context.getCacheDir(), "TreeAdapter");
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        bllFactory = (BllFactory) DatabaseManager.getInstance().getBllFactory(
                context,
                dir.getAbsolutePath(),
                "",
                DbType.Green_Dao,
                DbUsage.Personal
        );
    }

    /**
     * 生成模拟数据
     */
    private void generateData() {
        if (bllFactory == null) {
            return;
        }
        final DepartmentBll departmentBll = bllFactory.getDepartmentBll();
        final UserBll userBll = bllFactory.getUserBll();
        if (departmentBll.getCount() > 0) {
            departmentBll.deleteAll();
        }
        if (userBll.getCount() > 0) {
            userBll.deleteAll();
        }
        final int rootNum = 3;
        final int childNum = 5;
        final String baseDepartmentName = context.getString(R.string.str_department);
        final String baseUserName = context.getString(R.string.str_user);
        final List<Department> departmentList = new ArrayList<>();
        final List<User> userList = new ArrayList<>();

        for (int i = 0; i < rootNum; i++) {
            final String rootDepartmentId = ResHelper.getGuid();
            final String rootDepartmentName = baseDepartmentName + "_" + i;
            final String rootUserName = baseUserName + "_" + i;
            final Department department = generateDepartment(rootDepartmentId, rootDepartmentName, null);
            final User rootUser = generateUser(ResHelper.getGuid(), rootUserName, rootDepartmentId);
            userList.add(rootUser);
            departmentList.add(department);
            for (int j = 0; j < childNum; j++) {
                final String childDepartmentId = ResHelper.getGuid();
                final String childDepartmentName = rootDepartmentName + "_" + j;
                final String childUserName = rootUserName + "_" + j;
                final Department childDepartment = generateDepartment(childDepartmentId, childDepartmentName, rootDepartmentId);
                final User childUser = generateUser(ResHelper.getGuid(), childUserName, childDepartmentId);
                userList.add(childUser);
                departmentList.add(childDepartment);
                for (int k = 0; k < childNum; k++) {
                    final String leafDepartmentId = ResHelper.getGuid();
                    final String leafDepartmentName = childDepartmentName + "_" + k;
                    final String leafUserName = childUserName + "_" + k;
                    final Department leafDepartment = generateDepartment(leafDepartmentId, leafDepartmentName, childDepartmentId);
                    final User leafUser = generateUser(ResHelper.getGuid(), leafUserName, leafDepartmentId);
                    userList.add(leafUser);
                    departmentList.add(leafDepartment);
                }
            }
        }

        departmentBll.saveAll(departmentList);
        userBll.saveAll(userList);
    }


    /**
     * 生成部门
     *
     * @param uuid     ID
     * @param name     名字
     * @param parentId 父部门ID
     * @return 部门
     */
    private Department generateDepartment(String uuid, String name, String parentId) {
        final Department department = new Department();
        department.setUuid(uuid);
        department.setName(name);
        department.setNameSearchStr(Pinyin.toPinyin(name, ""));
        department.setParentId(parentId);
        return department;
    }

    /**
     * 生成用户
     *
     * @param uuid         ID
     * @param name         名字
     * @param departmentId 部门ID
     * @return 用户
     */
    private User generateUser(String uuid, String name, String departmentId) {
        final User user = new User();
        user.setUserId(uuid);
        user.setName(name);
        user.setNameSearchStr(Pinyin.toPinyin(name, ""));
        user.setDepartmentId(departmentId);
        return user;
    }

    /**
     * @return 系统窗口管理对象
     * 统一使用ApplicationContext获取窗口管理对象
     * @see <a href="https://www.jianshu.com/p/fa19fdc8268a/>
     */
    public WindowManager getWindowManager() {
        return getWindowManager(context);
    }

    /**
     * @return 系统窗口管理对象
     */
    private WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    /**
     * 获取节点列表
     *
     * @return 子节点列表
     */
    public List<TreeNode<DepartmentNode, UserLeaf>> getLocalNodeList() {
        if (bllFactory == null) {
            return new ArrayList<>();
        }
        final DepartmentBll departmentBll = bllFactory.getDepartmentBll();
        final List<TreeNode<DepartmentNode, UserLeaf>> childNodeList = new ArrayList<>();
        final List<Department> rootList = departmentBll.getRootList();
        for (Department department : rootList) {
            //对于顶级部门节点来说,其父节点是空,其用户节点为空
            final TreeNode<DepartmentNode, UserLeaf> treeNode = new TreeNode<>(new DepartmentNode(department), null, null);
            childNodeList.add(treeNode);
        }
        return childNodeList;
    }

    /**
     * 获取子节点列表
     *
     * @param node      节点
     * @param recursive 是否递归获取全部
     * @return 子节点列表
     */
    public List<TreeNode<DepartmentNode, UserLeaf>> getChildNodeList(TreeNode<DepartmentNode, UserLeaf> node, boolean recursive) {
        if (bllFactory == null || node == null || node.getCurrentNode() == null) {
            return new ArrayList<>();
        }
        final DepartmentBll departmentBll = bllFactory.getDepartmentBll();
        final UserBll userBll = bllFactory.getUserBll();
        final List<TreeNode<DepartmentNode, UserLeaf>> childNodeList = new ArrayList<>();
        final String currentDepId = node.getCurrentNode().getDepartment().getUuid();
        final List<Department> departmentList = departmentBll.getChildListById(currentDepId);
        final List<User> userList = userBll.getUserListByDepId(currentDepId);
        if (!userList.isEmpty()) {
            for (User user : userList) {
                //对于用户节点来说,其父节点是上级节点,其当前节点为空
                final TreeNode<DepartmentNode, UserLeaf> treeNode = new TreeNode<>(null, new UserLeaf(user), node);
                childNodeList.add(treeNode);
            }
        }
        if (!departmentList.isEmpty()) {
            for (Department department : departmentList) {
                //对于部门节点来说,其父节点是上级节点,其用户节点为空
                final TreeNode<DepartmentNode, UserLeaf> treeNode = new TreeNode<>(new DepartmentNode(department), null, node);
                if (recursive) {
                    childNodeList.addAll(getChildNodeList(treeNode, recursive));
                } else {
                    childNodeList.add(treeNode);
                }
            }
        }
        return childNodeList;
    }
}
