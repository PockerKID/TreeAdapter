package com.z.db.base.bll;

import android.database.Cursor;

import com.z.db.base.dal.BaseDal;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseBll
 *
 * @author KID
 * @date 2020/4/22.
 */
public abstract class BaseBll<T> {
    /**
     * dal对象引用
     */
    protected BaseDal<T> dal;

    /**
     * 初始化-构造函数
     *
     * @param dal dal对象
     */
    public BaseBll(BaseDal<T> dal) {
        this.dal = dal;
    }

    /**
     * 获取当前类型的dal,必须重写
     *
     * @return Dal
     */
    public abstract BaseDal<T> getDal();

    /**
     * 关闭数据库
     */
    public void close() {
        if (dal != null) {
            dal.close();
        }
    }

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 返回保存实体是否成功
     */
    public boolean save(T entity) {
        if (dal != null) {
            return dal.save(entity);
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
        if (dal != null) {
            return dal.saveAll(entities);
        }
        return false;
    }

    /**
     * 获取总记录数
     *
     * @return 总记录数
     */
    public long getCount() {
        if (dal != null) {
            return dal.getCount();
        }
        return 0;
    }

    /**
     * 查询所有实体
     *
     * @return 返回实体集合
     */
    public List<T> getAll() {
        if (dal != null) {
            return dal.getAll();
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
        if (dal != null) {
            return dal.delete(entity);
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
        if (dal != null) {
            return dal.delete(entities);
        }
        return false;
    }

    /**
     * 删除所有记录
     *
     * @return 返回删除是否成功
     */
    public boolean deleteAll() {
        if (dal != null) {
            return dal.deleteAll();
        }
        return false;
    }

    /**
     * 更新实体对象
     *
     * @param entity 实体对象
     */
    public boolean update(T entity) {
        if (dal != null) {
            return dal.update(entity);
        }
        return false;
    }

    /**
     * 批量更新实体对象
     *
     * @param entities 实体对象
     */
    public boolean update(List<T> entities) {
        if (dal != null) {
            return dal.update(entities);
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
        if (dal != null) {
            return dal.execSql(sql);
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
        if (dal != null) {
            return dal.execQuerySql(sql);
        }
        return null;
    }
}
