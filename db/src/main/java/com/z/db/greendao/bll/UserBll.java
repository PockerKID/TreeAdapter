package com.z.db.greendao.bll;

import com.z.db.greendao.dal.GreenDaoDal;
import com.z.db.greendao.dao.UserDao;
import com.z.db.greendao.entities.User;

import org.greenrobot.greendao.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * UserBll
 *
 * @author KID
 * @date 2020/4/22.
 */
public class UserBll extends GreenDaoBll<User> {
    /**
     * 初始化-构造函数
     *
     * @param dal dal对象
     */
    public UserBll(GreenDaoDal<User> dal) {
        super(dal);
    }

    /**
     * 根据部门ID获取用户列表
     *
     * @param depId 部门ID
     * @return 用户列表
     */
    public List<User> getUserListByDepId(String depId) {
        try {
            return getDal().getDbInterface().getAllByAndAsc(
                    new Property[]{
                            UserDao.Properties.Name,
                            UserDao.Properties.NameSearchStr
                    },
                    UserDao.Properties.DepartmentId.eq(depId)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
