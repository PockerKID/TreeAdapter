package com.z.db;

import android.content.Context;

import com.z.db.base.bll.BaseBllFactory;
import com.z.db.base.dao.DbType;
import com.z.db.greendao.bll.BllFactory;
import com.z.db.greendao.manager.DbUsage;

import java.util.HashMap;
import java.util.Map;

/**
 * DatabaseManager
 *
 * @author KID
 * @date 2020/4/22.
 */
public class DatabaseManager {
    /**
     * TAG
     */
    private static final String TAG = DatabaseManager.class.getSimpleName();


    /**
     * DatabaseManager实例
     */
    private static volatile DatabaseManager instance;

    /**
     * BaseBllFactory
     */
    private Map<DbUsage, Map<String, BaseBllFactory>> baseBllFactory = new HashMap<>();

    /**
     * 构造函数
     */
    public DatabaseManager() {
        for (DbUsage dbUsage : DbUsage.values()) {
            baseBllFactory.put(dbUsage, new HashMap<String, BaseBllFactory>());
        }
    }

    /**
     * 使用单例模式获得操作数据库的对象
     *
     * @return {@link DatabaseManager}
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取数据库管理对象
     *
     * @param dbType 数据库类型
     * @return 数据库管理对象
     */
    public BaseBllFactory getBllFactory(Context context, String dbPath, String dbName, DbType dbType, DbUsage dbUsage) {
        switch (dbType) {
            default:
            case Green_Dao:
                //完成初始化
                if (getBaseBllFactory(dbUsage, dbName) == null) {
                    Map<String, BaseBllFactory> dbBaseBllFactoryMap = getBaseBllFactoryMap(dbUsage);
                    if (dbBaseBllFactoryMap == null) {
                        dbBaseBllFactoryMap = new HashMap<>();
                    }
                    dbBaseBllFactoryMap.put(dbName, BllFactory.getInstance(context, dbPath, dbName, dbUsage));
                    baseBllFactory.put(dbUsage, dbBaseBllFactoryMap);
                }
                break;
        }
        return getBaseBllFactory(dbUsage, dbName);
    }

    /**
     * 获取数据库管理对象
     *
     * @param dbUsage 数据库类型
     * @return 数据库管理对象
     */
    private BaseBllFactory getBaseBllFactory(DbUsage dbUsage, String dbName) {
        Map<String, BaseBllFactory> dbBaseBllFactoryMap = getBaseBllFactoryMap(dbUsage);
        return dbBaseBllFactoryMap == null ? null : dbBaseBllFactoryMap.get(dbName);
    }


    /**
     * 获取数据库管理对象
     *
     * @param dbUsage 数据库类型
     * @return 数据库管理对象
     */
    private Map<String, BaseBllFactory> getBaseBllFactoryMap(DbUsage dbUsage) {
        return baseBllFactory.get(dbUsage);
    }

    /**
     * 关闭
     */
    public void close(DbUsage dbUsage) {
        Map<String, BaseBllFactory> baseBllFactoryMap = getBaseBllFactoryMap(dbUsage);
        if (baseBllFactoryMap != null) {
            for (Map.Entry<String, BaseBllFactory> item : baseBllFactoryMap.entrySet()) {
                item.getValue().close();
            }
            baseBllFactoryMap.clear();
            baseBllFactory.remove(dbUsage);
        }
    }

    /**
     * 关闭
     */
    public void close() {
        for (DbUsage dbUsage : DbUsage.values()) {
            close(dbUsage);
        }
        baseBllFactory.clear();
        if (instance != null) {
            instance = null;
        }
    }
}
