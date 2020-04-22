package com.z.db.greendao.dal;

import com.z.db.base.dal.BaseDal;
import com.z.db.greendao.GreenDaoInterface;

/**
 * GreenDaoDal
 *
 * @author KID
 * @date 2020/4/22.
 */
public class GreenDaoDal<T> extends BaseDal<T> {


    /**
     * 初始化-构造函数
     *
     * @param iDbInterface 框架数据库操作对象
     * @param entityClass  T实体类
     */
    public GreenDaoDal(GreenDaoInterface<T> iDbInterface, Class<T> entityClass) {
        super(iDbInterface, entityClass);
        if (dbInterface != null) {
            ((GreenDaoInterface<T>) dbInterface).setUpDao(entityClass);
        }
    }

    @Override
    public GreenDaoInterface<T> getDbInterface() {
        return (GreenDaoInterface<T>) super.getDbInterface();
    }
}
