package com.z.db.greendao.bll;

import com.z.db.greendao.dal.GreenDaoDal;
import com.z.db.greendao.dao.DepartmentDao;
import com.z.db.greendao.entities.Department;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * DepartmentBll
 *
 * @author KID
 * @date 2020/4/22.
 */
public class DepartmentBll extends GreenDaoBll<Department> {
    /**
     * 初始化-构造函数
     *
     * @param dal dal对象
     */
    public DepartmentBll(GreenDaoDal<Department> dal) {
        super(dal);
    }

    /**
     * 获取根节点列表
     *
     * @return 根节点列表
     */
    public List<Department> getRootList() {
        try {
            return getDal().getDbInterface().getAllByAnd(
                    DepartmentDao.Properties.ParentId.isNull()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据ID获取子节点列表
     *
     * @param id ID
     * @return 子节点列表
     */
    public List<Department> getChildListById(String id) {
        try {
            return getDal().getDbInterface().getAllByAndAsc(
                    new Property[]{
                            DepartmentDao.Properties.Name,
                            DepartmentDao.Properties.NameSearchStr
                    },
                    DepartmentDao.Properties.ParentId.eq(id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
