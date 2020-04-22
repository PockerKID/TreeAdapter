package com.z.db.greendao.dal;

import com.z.db.greendao.GreenDaoInterface;
import com.z.db.greendao.entities.ErrorLog;

/**
 * ErrorLogDal
 *
 * @author KID
 * @date 2020/4/22.
 */
public class ErrorLogDal extends GreenDaoDal<ErrorLog> {
    /**
     * 初始化-构造函数
     *
     * @param iDbInterface 框架数据库操作对象
     * @param entityClass  T实体类
     */
    public ErrorLogDal(GreenDaoInterface<ErrorLog> iDbInterface, Class<ErrorLog> entityClass) {
        super(iDbInterface, entityClass);
    }
}
