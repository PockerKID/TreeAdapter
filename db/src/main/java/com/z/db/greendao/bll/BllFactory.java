package com.z.db.greendao.bll;

import android.content.Context;

import com.z.db.base.bll.BaseBllFactory;
import com.z.db.greendao.dal.DalFactory;
import com.z.db.greendao.manager.DbUsage;

import java.util.HashMap;
import java.util.Map;

/**
 * BllFactory
 *
 * @author KID
 * @date 2020/4/22.
 */
public class BllFactory implements BaseBllFactory {
    /**
     * TAG
     */
    private static final String TAG = BllFactory.class.getSimpleName();

    /**
     * 单例对象
     */
    private static Map<DbUsage, BllFactory> instance = new HashMap<>();

    /**
     * Dal工厂
     */
    private DalFactory dalFactory;

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param dbPath  数据库保存路径
     * @param dbName  数据库名称
     */
    private BllFactory(Context context, String dbPath, String dbName, DbUsage dbUsage) {
        dalFactory = DalFactory.getInstance(context, dbPath, dbName, dbUsage);
    }

    /**
     * 获取单例对象
     *
     * @param context 上下文
     * @param dbPath  数据库保存路径
     * @param dbName  数据库名称
     * @return 单例对象
     */
    public static BaseBllFactory getInstance(Context context, String dbPath, String dbName, DbUsage dbUsage) {
        if (getInstance(dbUsage) == null) {
            instance.put(dbUsage, new BllFactory(context, dbPath, dbName, dbUsage));
        }
        return getInstance(dbUsage);
    }

    /**
     * 从Map中取出对应实例
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     * @return 对应实例
     */
    private static BllFactory getInstance(DbUsage dbUsage) {
        return instance == null ? null : instance.get(dbUsage);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        if (dalFactory != null) {
            dalFactory.close();
        }
        dalFactory = null;
        instance.clear();

        errorLogBll = null;
        departmentBll = null;
        userBll = null;
    }

    //Bll
    //region

    private ErrorLogBll errorLogBll;
    private DepartmentBll departmentBll;
    private UserBll userBll;

    /**
     * @return 错误日志信息表操作对象
     */
    public ErrorLogBll getErrorLogBll() {
        if (errorLogBll == null) {
            errorLogBll = new ErrorLogBll(dalFactory.getErrorLogDal());
        }
        return errorLogBll;
    }

    /**
     * @return 部门信息表操作对象
     */
    public DepartmentBll getDepartmentBll() {
        if (departmentBll == null) {
            departmentBll = new DepartmentBll(dalFactory.getDepartmentDal());
        }
        return departmentBll;
    }

    /**
     * @return 用户信息表操作对象
     */
    public UserBll getUserBll() {
        if (userBll == null) {
            userBll = new UserBll(dalFactory.getUserDal());
        }
        return userBll;
    }

    //endregion
}
