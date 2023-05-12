package com.z.treeadapter.model;

import com.z.db.greendao.entities.Department;
import com.z.treeadapter.tree.model.BaseNode;

/**
 * DepartmentNode
 *
 * @author KID
 * @date 2020/4/22.
 */
public class DepartmentNode extends BaseNode {

    /**
     * 部门节点
     */
    private Department department;

    /**
     * 构造函数
     */
    public DepartmentNode() {
    }

    /**
     * 构造函数
     *
     * @param department 部门节点
     */
    public DepartmentNode(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}