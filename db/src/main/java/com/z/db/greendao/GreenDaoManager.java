package com.z.db.greendao;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.z.db.greendao.dao.DaoSession;
import com.z.db.greendao.manager.DaoManager;
import com.z.db.greendao.manager.DbUsage;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * GreenDaoManager
 *
 * @author KID
 * @date 2020/4/22.
 */
public class GreenDaoManager {
    /**
     * TAG
     */
    private static final String TAG = GreenDaoManager.class.getSimpleName();

    /**
     * GreenDaoManager实例
     */
    private static volatile GreenDaoManager instance;

    /**
     * 默认数据库路径
     */
    private static final String DB_PATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 默认数据库名称
     */
    private static final String DB_NAME = "data.db";

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return {@link GreenDaoManager}
     */
    public static GreenDaoManager getInstance() {
        if (instance == null) {
            synchronized (GreenDaoManager.class) {
                if (instance == null) {
                    instance = new GreenDaoManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取DaoSession
     *
     * @param context 上下文
     * @param dbPath  数据库路径
     * @param dbName  数据库名称
     * @param dbUsage 数据库用途类型{@link DbUsage}
     * @return {@link DaoSession}
     */
    public synchronized DaoSession getDaoSession(Context context, String dbPath, String dbName, DbUsage dbUsage) {
        if (DaoManager.getDaoSession(dbUsage) == null) {
            final String tempPath = TextUtils.isEmpty(dbPath) ? DB_PATH : dbPath;
            final String tempName = TextUtils.isEmpty(dbName) ? DB_NAME : dbName;
            DaoManager.initSessions(context, tempPath, tempName, dbUsage);
        }
        return DaoManager.getDaoSession(dbUsage);
    }

    /**
     * 设置debug模式开启或关闭(默认关闭)
     *
     * @param flag 是否开启
     */
    public void setDebug(boolean flag) {
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    /**
     * 关闭数据库连接
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    public synchronized void closeDataBase(DbUsage dbUsage) {
        closeDaoSession(dbUsage);
    }

    /**
     * 关闭数据库连接
     */
    public synchronized void closeDataBase() {
        closeDaoSession();
    }

    /**
     * 关闭连接
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    private void closeDaoSession(DbUsage dbUsage) {
        DaoManager.closeDaoSession(dbUsage);
    }

    /**
     * 关闭连接
     */
    private void closeDaoSession() {
        DaoManager.closeDaoSession();
    }
}

