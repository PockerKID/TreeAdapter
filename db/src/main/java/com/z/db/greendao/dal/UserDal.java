package com.z.db.greendao.dal;

import com.z.db.greendao.GreenDaoInterface;
import com.z.db.greendao.entities.User;

/**
 * UserDal
 *
 * @author KID
 * @date 2020/4/22.
 */
public class UserDal extends GreenDaoDal<User> {
    /**
     * 初始化-构造函数
     *
     * @param iDbInterface 框架数据库操作对象
     * @param entityClass  T实体类
     */
    public UserDal(GreenDaoInterface<User> iDbInterface, Class<User> entityClass) {
        super(iDbInterface, entityClass);
    }
}
