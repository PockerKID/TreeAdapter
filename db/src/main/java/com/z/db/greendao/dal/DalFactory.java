package com.z.db.greendao.dal;

import android.content.Context;

import com.z.db.base.dal.BaseDalFactory;
import com.z.db.greendao.GreenDaoInterfaceImpl;
import com.z.db.greendao.GreenDaoManager;
import com.z.db.greendao.entities.Department;
import com.z.db.greendao.entities.ErrorLog;
import com.z.db.greendao.entities.User;
import com.z.db.greendao.manager.DbUsage;

import java.util.HashMap;
import java.util.Map;

/**
 * DalFactory
 *
 * @author KID
 * @date 2020/4/22.
 */
public class DalFactory implements BaseDalFactory {

    /**
     * TAG
     */
    private static final String TAG = DalFactory.class.getSimpleName();

    /**
     * 实例
     */
    private static Map<DbUsage, DalFactory> instance = new HashMap<>();

    /**
     * DaoManager
     */
    private GreenDaoManager greenDaoManager;

    /**
     * 上下文
     */
    private Context context;

    /**
     * 数据库路径
     */
    private String dbPath;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库用途类型{@link DbUsage}
     */
    private DbUsage dbUsage;

    /**
     * 构造函数
     */
    public DalFactory() {
        greenDaoManager = GreenDaoManager.getInstance();
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     * @param dbPath  数据库路径
     * @param dbName  数据库名称
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    public DalFactory(Context context, String dbPath, String dbName, DbUsage dbUsage) {
        this.context = context;
        this.dbPath = dbPath;
        this.dbName = dbName;
        this.greenDaoManager = GreenDaoManager.getInstance();
        this.dbUsage = dbUsage;
    }

    /**
     * 获取DbFactory单例对象
     *
     * @param context 上下文
     * @param dbPath  数据库路径
     * @param dbName  数据库名称
     * @param dbUsage 数据库用途类型{@link DbUsage}
     * @return DbFactory单例对象
     */
    public static DalFactory getInstance(Context context, String dbPath, String dbName, DbUsage dbUsage) {
        if (getInstance(dbUsage) == null) {
            instance.put(dbUsage, new DalFactory(context, dbPath, dbName, dbUsage));
        }
        return getInstance(dbUsage);
    }

    /**
     * 从Map中取出对应实例
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     * @return 对应实例
     */
    private static DalFactory getInstance(DbUsage dbUsage) {
        return instance == null ? null : instance.get(dbUsage);
    }

    /**
     * 关闭
     */
    @Override
    public void close() {
        if (greenDaoManager != null) {
            greenDaoManager.closeDataBase(dbUsage);
        }
        greenDaoManager = null;
        instance.remove(dbUsage);

        errorLogDal = null;
        departmentDal = null;
        userDal = null;
    }

    //Dal
    //region

    private ErrorLogDal errorLogDal;
    private DepartmentDal departmentDal;
    private UserDal userDal;

    /**
     * @return 错误日志信息表操作对象
     */
    public ErrorLogDal getErrorLogDal() {
        if (errorLogDal == null) {
            errorLogDal = new ErrorLogDal(new GreenDaoInterfaceImpl<ErrorLog>().getInstance(
                    context, dbPath, dbName, dbUsage
            ), ErrorLog.class);
        }
        return errorLogDal;
    }

    /**
     * @return 部门信息表操作对象
     */
    public DepartmentDal getDepartmentDal() {
        if (departmentDal == null) {
            departmentDal = new DepartmentDal(new GreenDaoInterfaceImpl<Department>().getInstance(
                    context, dbPath, dbName, dbUsage
            ), Department.class);
        }
        return departmentDal;
    }

    /**
     * @return 用户信息表操作对象
     */
    public UserDal getUserDal() {
        if (userDal == null) {
            userDal = new UserDal(new GreenDaoInterfaceImpl<User>().getInstance(
                    context, dbPath, dbName, dbUsage
            ), User.class);
        }
        return userDal;
    }

    //endregion
}
