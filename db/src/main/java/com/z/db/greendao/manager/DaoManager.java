package com.z.db.greendao.manager;

import android.content.Context;

import com.z.db.greendao.GreenDaoContext;
import com.z.db.greendao.dao.DaoMaster;
import com.z.db.greendao.dao.DaoSession;

import java.util.HashMap;
import java.util.Map;

/**
 * DaoManager
 *
 * @author KID
 * @date 2020/4/22.
 */
public class DaoManager {
    /**
     * TAG
     */
    private static final String TAG = DaoManager.class.getSimpleName();

    /**
     * DaoSessionMap
     */
    private final static Map<String, DaoMaster> DAO_MASTER_MAP = new HashMap<>();

    /**
     * DaoSessionMap
     */
    private final static Map<String, DaoSession> DAO_SESSION_MAP = new HashMap<>();

    /**
     * 添加数据库
     *
     * @param key       Key
     * @param daoMaster {@link DaoMaster}
     */
    private static void addDaoMaster(String key, DaoMaster daoMaster) {
        try {
            DAO_MASTER_MAP.put(key, daoMaster);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除据库
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    private static void removeDaoMaster(DbUsage dbUsage) {
        try {
            final DaoMaster daoMaster = DAO_MASTER_MAP.get(dbUsage.name());
            if (daoMaster != null) {
                daoMaster.getDatabase().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DAO_MASTER_MAP.remove(dbUsage.name());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加数据库连接
     *
     * @param key        Key
     * @param daoSession {@link DaoSession}
     */
    private static void addDaoSession(String key, DaoSession daoSession) {
        try {
            DAO_SESSION_MAP.put(key, daoSession);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除据库连接
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    private static void removeDaoSession(DbUsage dbUsage) {
        try {
            DAO_SESSION_MAP.remove(dbUsage.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据库连接
     *
     * @param context 上下文
     * @param dbPath  数据库路径
     * @param dbName  数据库名称
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    public static void initSessions(Context context, String dbPath, String dbName, DbUsage dbUsage) {
        final GreenDaoContext greenDaoContext = new GreenDaoContext(context);
        greenDaoContext.setDbDir(dbPath);
        switch (dbUsage) {
            case Personal:
                final PersonalDbDaoMaster.DevOpenHelper personalDevOpenHelper = new PersonalDbDaoMaster.DevOpenHelper(greenDaoContext, dbName, null);
                PersonalDbDaoMaster personalDbDaoMaster = new PersonalDbDaoMaster(personalDevOpenHelper.getWritableDb());
                addDaoMaster(dbUsage.name(), personalDbDaoMaster);
                addDaoSession(dbUsage.name(), personalDbDaoMaster.newSession());
                break;
            case Log:
                final LogDbDaoMaster.DevOpenHelper logDevOpenHelper = new LogDbDaoMaster.DevOpenHelper(greenDaoContext, dbName, null);
                LogDbDaoMaster logDbDaoMaster = new LogDbDaoMaster(logDevOpenHelper.getWritableDb());
                addDaoMaster(dbUsage.name(), logDbDaoMaster);
                addDaoSession(dbUsage.name(), logDbDaoMaster.newSession());
                break;
            default:
                break;
        }
    }

    /**
     * 获取数据库连接
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     * @return 据库连接
     */
    public static DaoSession getDaoSession(DbUsage dbUsage) {
        try {
            return DAO_SESSION_MAP.get(dbUsage.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    public static void closeDaoSession(DbUsage dbUsage) {
        final DaoSession daoSession = getDaoSession(dbUsage);
        if (daoSession != null) {
            daoSession.clear();
        }
        removeDaoSession(dbUsage);
    }

    /**
     * 获取数据库连接
     *
     * @return 据库连接
     */
    public static Map<String, DaoSession> getDaoSessions() {
        return DAO_SESSION_MAP;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeDaoSession() {
        for (DbUsage dbUsage : DbUsage.values()) {
            closeDaoSession(dbUsage);
        }
    }


    /**
     * 关闭数据库
     *
     * @param dbUsage 数据库用途类型{@link DbUsage}
     */
    public static void closeDaoMaster(DbUsage dbUsage) {
        removeDaoMaster(dbUsage);
    }

    /**
     * 关闭数据库连接
     */
    public static void closeDaoMaster() {
        for (DbUsage dbUsage : DbUsage.values()) {
            closeDaoMaster(dbUsage);
        }
    }
}