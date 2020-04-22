package com.z.db.greendao;

import com.z.db.base.dao.DbInterface;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * GreenDaoInterface
 *
 * @author KID
 * @date 2020/4/22.
 */
public interface GreenDaoInterface<T> extends DbInterface<T> {

    /**
     * 装载数库DAO
     *
     * @param entityClass T实体类
     */
    void setUpDao(Class<T> entityClass);

    /**
     * 获取数据库DAO
     * @return 数据库DAO
     */
    AbstractDao<T, ?> getDao();

    /**
     * 获取数据
     *
     * @param properties 字段
     * @return 符合条件的数据
     */
    T getFirst(Property... properties);

    /**
     * 获取数据
     *
     * @param properties 字段
     * @return 符合条件的数据
     */
    T getLast(Property... properties);

    /**
     * 获取数据
     *
     * @param cond     条件
     * @param condMore 条件
     * @return 符合条件的数据
     */
    T getSingleByAnd(WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取数据
     *
     * @param cond     条件
     * @param cond1    条件
     * @param condMore 条件
     * @return 符合条件的数据
     */
    T getSingleByOr(WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param condMore   条件
     * @return 符合条件的数据
     */
    T getFirstByAnd(Property[] properties, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param cond1      条件
     * @param condMore   条件
     * @return 符合条件的数据
     */
    T getFirstByOr(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param condMore   条件
     * @return 符合条件的数据
     */
    T getLastByAnd(Property[] properties, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param cond1      条件
     * @param condMore   条件
     * @return 符合条件的数据
     */
    T getLastByOr(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param cond     条件
     * @param condMore 条件
     * @return 符合条件的数据集
     */
    List<T> getAllByAnd(WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param cond     条件
     * @param cond1    条件
     * @param condMore 条件
     * @return 符合条件的数据集
     */
    List<T> getAllByOr(WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param num      条数
     * @param cond     条件
     * @param condMore 条件
     * @return 符合条件的数据集
     */
    List<T> getAllByAnd(int num, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param num      条数
     * @param cond     条件
     * @param cond1    条件
     * @param condMore 条件
     * @return 符合条件的数据集
     */
    List<T> getAllByOr(int num, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @return 符合条件的数据集
     */
    List<T> getAllByAsc(Property... properties);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @return 符合条件的数据集
     */
    List<T> getAllByDesc(Property... properties);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByAndAsc(Property[] properties, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param cond1      条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByOrAsc(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByAndDesc(Property[] properties, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param cond       条件
     * @param cond1      条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByOrDesc(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param num        条数
     * @param cond       条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByAndAsc(Property[] properties, int num, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param num        条数
     * @param cond       条件
     * @param cond1      条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByOrAsc(Property[] properties, int num, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param num        条数
     * @param cond       条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByAndDesc(Property[] properties, int num, WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取所有数据
     *
     * @param properties 字段
     * @param num        条数
     * @param cond       条件
     * @param cond1      条件
     * @param condMore   条件
     * @return 符合条件的数据集
     */
    List<T> getAllByOrDesc(Property[] properties, int num, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);

    /**
     * 获取符合条件的数据条数
     *
     * @param cond     条件
     * @param condMore 条件
     * @return 条数
     */
    long getCountByAnd(WhereCondition cond, WhereCondition... condMore);

    /**
     * 获取符合条件的数据条数
     *
     * @param cond     条件
     * @param cond1    条件
     * @param condMore 条件
     * @return 条数
     */
    long getCountByOr(WhereCondition cond, WhereCondition cond1, WhereCondition... condMore);
}