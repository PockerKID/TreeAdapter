package com.z.db.greendao;

import android.content.Context;
import android.database.Cursor;

import com.z.db.greendao.manager.DbUsage;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * GreenDaoInterfaceImpl
 *
 * @author KID
 * @date 2020/4/22.
 */
public class GreenDaoInterfaceImpl<T> implements GreenDaoInterface<T> {

    /**
     * 实例
     */
    protected GreenDaoInterface<T> instance;

    /**
     * Dao
     */
    protected AbstractDao<T, ?> dao;

    /**
     * 上下文
     */
    protected Context context;

    /**
     * 数据库路径
     */
    protected String dbPath;

    /**
     * 数据库名称
     */
    protected String dbName;

    /**
     * 数据库用途类型{@link DbUsage}
     */
    protected DbUsage dbUsage;

    /**
     * 数据库管理对象
     */
    private GreenDaoManager greenDaoManager;

    /**
     * 初始化
     *
     * @param daoManager 数据库管理对象
     */
    private void init(GreenDaoManager daoManager) {
        this.greenDaoManager = daoManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setUpDao(Class<T> entityClass) {
        try {
            this.dao = (AbstractDao<T, ?>) greenDaoManager.getDaoSession(context, dbPath, dbName, dbUsage).getDao(entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AbstractDao<T, ?> getDao() {
        return dao;
    }

    @Override
    public T getFirst(Property... properties) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getLast(Property... properties) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getSingleByAnd(WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().where(cond, condMore).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getSingleByOr(WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().whereOr(cond, cond1, condMore).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getFirstByAnd(Property[] properties, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).where(cond, condMore).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getFirstByOr(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).whereOr(cond, cond1, condMore).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getLastByAnd(Property[] properties, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).where(cond, condMore).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public T getLastByOr(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).whereOr(cond, cond1, condMore).limit(1).unique();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<T> getAllByAnd(WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().where(cond, condMore).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByOr(WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().whereOr(cond, cond1, condMore).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByAnd(int num, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().where(cond, condMore).limit(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByOr(int num, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().whereOr(cond, cond1, condMore).limit(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByAsc(Property... properties) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByDesc(Property... properties) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByAndAsc(Property[] properties, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).where(cond, condMore).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByOrAsc(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).whereOr(cond, cond1, condMore).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByAndDesc(Property[] properties, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).where(cond, condMore).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByOrDesc(Property[] properties, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).whereOr(cond, cond1, condMore).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByAndAsc(Property[] properties, int num, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).where(cond, condMore).limit(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByOrAsc(Property[] properties, int num, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderAsc(properties).whereOr(cond, cond1, condMore).limit(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByAndDesc(Property[] properties, int num, WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).where(cond, condMore).limit(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public List<T> getAllByOrDesc(Property[] properties, int num, WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().orderDesc(properties).whereOr(cond, cond1, condMore).limit(num).list();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public long getCountByAnd(WhereCondition cond, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().where(cond, condMore).count();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public long getCountByOr(WhereCondition cond, WhereCondition cond1, WhereCondition... condMore) {
        if (dao != null) {
            try {
                return dao.queryBuilder().whereOr(cond, cond1, condMore).count();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public GreenDaoInterface<T> getInstance(Context context, String dbPath, String dbName, Object param) {
        if (instance == null) {
            instance = this;
            this.context = context;
            this.dbPath = dbPath;
            this.dbName = dbName;
            this.dbUsage = (DbUsage) param;
            init(GreenDaoManager.getInstance());
        }
        return instance;
    }

    @Override
    public void close() {
        if (greenDaoManager != null) {
            greenDaoManager.closeDataBase(dbUsage);
        }
        greenDaoManager = null;
        dao = null;
    }

    @Override
    public boolean save(T entity) {
        if (dao != null) {
            try {
                dao.insertOrReplace(entity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean saveAll(List<T> entities) {
        if (dao != null) {
            try {
                dao.insertOrReplaceInTx(entities);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public long getCount() {
        if (dao != null) {
            try {
                return dao.count();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public List<T> getAll() {
        if (dao != null) {
            try {
                return dao.loadAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<T>();
    }

    @Override
    public boolean delete(T entity) {
        if (dao != null) {
            try {
                dao.delete(entity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(List<T> entities) {
        if (dao != null) {
            try {
                dao.deleteInTx(entities);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        if (dao != null) {
            try {
                dao.deleteAll();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(T entity) {
        if (dao != null) {
            try {
                dao.update(entity);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(List<T> entities) {
        if (dao != null) {
            try {
                dao.updateInTx(entities);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean execSql(String sql) {
        if (dao != null) {
            try {
                dao.getDatabase().rawQuery(sql, null);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Cursor execQuerySql(String sql) {
        if (dao != null) {
            try {
                return dao.getDatabase().rawQuery(sql, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}