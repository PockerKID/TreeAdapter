package com.z.db.base.dal;

import android.database.Cursor;

import com.z.db.base.dao.DbInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseDal
 *
 * @author KID
 * @date 2020/4/22.
 */
public class BaseDal<T> {

    /**
     * 数据库操作对象
     */
    protected DbInterface<T> dbInterface;

    /**
     * 初始化-构造函数
     *
     * @param dbInterface 框架数据库操作对象
     * @param entityClass T实体类
     */
    public BaseDal(DbInterface<T> dbInterface, Class<T> entityClass) {
        this.dbInterface = dbInterface;
    }

    /**
     * @return 框架数据库操作对象
     */
    public DbInterface<T> getDbInterface() {
        return dbInterface;
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (dbInterface != null) {
            dbInterface.close();
        }
    }

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 返回保存实体是否成功
     */
    public boolean save(T entity) {
        if (dbInterface != null) {
            return dbInterface.save(entity);
        }
        return false;
    }

    /**
     * 保存实体集合
     *
     * @param entities 实体对象集合
     * @return 返回保存实体集合是否成功
     */
    public boolean saveAll(List<T> entities) {
        if (dbInterface != null) {
            return dbInterface.saveAll(entities);
        }
        return false;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public long getCount() {
        if (dbInterface != null) {
            return dbInterface.getCount();
        }
        return 0;
    }

    /**
     * 查询所有实体
     *
     * @return 返回实体集合
     */
    public List<T> getAll() {
        if (dbInterface != null) {
            return dbInterface.getAll();
        }
        return new ArrayList<>();
    }

    /**
     * 删除实体
     *
     * @param entity 实体对象
     * @return 返回删除是否成功
     */
    public boolean delete(T entity) {
        if (dbInterface != null) {
            return dbInterface.delete(entity);
        }
        return false;
    }

    /**
     * 批量删除实体
     *
     * @param entities 实体对象集合
     * @return 返回删除是否成功
     */
    public boolean delete(List<T> entities) {
        if (dbInterface != null) {
            return dbInterface.delete(entities);
        }
        return false;
    }

    /**
     * 删除所有记录
     *
     * @return 返回删除是否成功
     */
    public boolean deleteAll() {
        if (dbInterface != null) {
            return dbInterface.deleteAll();
        }
        return false;
    }

    /**
     * 更新实体对象
     *
     * @param entity 实体对象
     */
    public boolean update(T entity) {
        if (dbInterface != null) {
            return dbInterface.update(entity);
        }
        return false;
    }

    /**
     * 批量更新实体对象
     *
     * @param entities 实体对象
     */
    public boolean update(List<T> entities) {
        if (dbInterface != null) {
            return dbInterface.update(entities);
        }
        return false;
    }

    /**
     * 执行外界sql语句
     *
     * @param sql 需要执行的sql语句
     * @return 返回是否操作成功
     */
    public boolean execSql(String sql) {
        if (dbInterface != null) {
            return dbInterface.execSql(sql);
        }
        return false;
    }

    /**
     * 执行外界sql语句
     *
     * @param sql 需要执行的sql语句
     * @return 返回操作结果
     */
    public Cursor execQuerySql(String sql) {
        if (dbInterface != null) {
            return dbInterface.execQuerySql(sql);
        }
        return null;
    }
}