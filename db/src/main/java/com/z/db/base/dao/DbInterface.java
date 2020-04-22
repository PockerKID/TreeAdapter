package com.z.db.base.dao;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

/**
 * DbInterface
 *
 * @author KID
 * @date 2020/4/22.
 */
public interface DbInterface<T> {

    /**
     * 获取接口对象
     *
     * @param context 上下文
     * @param dbPath  数据库路径
     * @param dbName  数据库名称
     * @param param   其他参数
     * @return 返回接口对象
     */
    DbInterface<T> getInstance(Context context, String dbPath, String dbName, Object param);

    /**
     * 关闭数据库
     */
    void close();

    /**
     * 保存实体
     *
     * @param entity 实体对象
     * @return 返回保存实体是否成功
     */
    boolean save(T entity);

    /**
     * 保存实体集合
     *
     * @param entities 实体对象集合
     * @return 返回保存实体集合是否成功
     */
    boolean saveAll(List<T> entities);

    /**
     * 获取记录条数
     *
     * @return 记录条数
     */
    long getCount();

    /**
     * 查询所有实体
     *
     * @return 返回实体集合
     */
    List<T> getAll();

    /**
     * 删除实体
     *
     * @param entity 实体对象
     * @return 返回删除是否成功
     */
    boolean delete(T entity);

    /**
     * 批量删除实体
     *
     * @param entities 实体对象集合
     * @return 返回删除是否成功
     */
    boolean delete(List<T> entities);

    /**
     * 删除所有记录
     *
     * @return 返回删除是否成功
     */
    boolean deleteAll();

    /**
     * 更新实体对象
     *
     * @param entity 实体对象
     * @return 返回是否操作成功
     */
    boolean update(T entity);

    /**
     * 批量更新实体对象
     *
     * @param entities 实体对象
     * @return 返回是否操作成功
     */
    boolean update(List<T> entities);

    /**
     * 执行外界sql语句
     *
     * @param sql 需要执行的sql语句
     * @return 返回是否操作成功
     */
    boolean execSql(String sql);

    /**
     * 执行外界sql语句
     *
     * @param sql 需要执行的sql语句
     * @return 返回操作结果
     */
    Cursor execQuerySql(String sql);
}