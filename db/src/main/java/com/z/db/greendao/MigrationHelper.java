package com.z.db.greendao;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MigrationHelper
 * 数据库升级辅助类(创建临时表)
 *
 * @author KID
 * @date 2020/4/22.
 * <p>
 * Please call {@link #migrate(SQLiteDatabase, Class[])} or {@link #migrate(Database, Class[])}
 */
public class MigrationHelper {

    /**
     * TAG
     */
    private static final String TAG = MigrationHelper.class.getSimpleName();

    /**
     * 是否是调试
     */
    public static boolean DEBUG = false;

    /**
     * TAG
     */
    private static final String SQLITE_MASTER = "sqlite_master";
    /**
     * 临时表TAG
     */
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

    /**
     * 监听
     */
    private static WeakReference<ReCreateAllTableListener> weakListener;

    /**
     * 监听
     */
    public interface ReCreateAllTableListener {
        /**
         * 表创建
         *
         * @param db          数据库
         * @param ifNotExists 如果不存在
         */
        void onCreateAllTables(Database db, boolean ifNotExists);

        /**
         * 表删除
         *
         * @param db       数据库
         * @param ifExists 如果存在
         */
        void onDropAllTables(Database db, boolean ifExists);
    }

    /**
     * 升级数据库
     *
     * @param db         数据库
     * @param daoClasses 表
     */
    @SafeVarargs
    public static void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("[The Old Database Version]" + db.getVersion());
        Database database = new StandardDatabase(db);
        migrate(database, daoClasses);
    }

    /**
     * 升级数据库
     *
     * @param db         数据库
     * @param listener   监听
     * @param daoClasses 表
     */
    @SafeVarargs
    public static void migrate(SQLiteDatabase db, ReCreateAllTableListener listener, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        weakListener = new WeakReference<>(listener);
        migrate(db, daoClasses);
    }

    /**
     * 升级数据库
     *
     * @param database   数据库
     * @param listener   监听
     * @param daoClasses 表
     */
    @SafeVarargs
    public static void migrate(Database database, ReCreateAllTableListener listener, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        weakListener = new WeakReference<>(listener);
        migrate(database, daoClasses);
    }

    /**
     * 升级数据库
     *
     * @param database   数据库
     * @param daoClasses 表
     */
    @SafeVarargs
    public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("[Generate temp table]start");
        generateTempTables(database, daoClasses);
        printLog("[Generate temp table]complete");

        ReCreateAllTableListener listener = null;
        if (weakListener != null) {
            listener = weakListener.get();
        }

        if (listener != null) {
            listener.onDropAllTables(database, true);
            printLog("[Drop all table by listener]");
            listener.onCreateAllTables(database, false);
            printLog("[Create all table by listener]");
        } else {
            dropAllTables(database, true, daoClasses);
            createAllTables(database, false, daoClasses);
        }
        printLog("[Restore data]start");
        restoreData(database, daoClasses);
        printLog("[Restore data]complete");
    }

    /**
     * 创建临时表
     *
     * @param db         数据库
     * @param daoClasses 表
     */
    @SafeVarargs
    private static void generateTempTables(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            String tempTableName = null;

            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            if (!isTableExists(db, false, tableName)) {
                printLog("[New Table]" + tableName);
                continue;
            }
            try {
                tempTableName = daoConfig.tablename.concat("_TEMP");
                db.execSQL("DROP TABLE IF EXISTS " + tempTableName + ";");
                String insertTableStringBuilder = "CREATE TEMPORARY TABLE " + tempTableName + " AS SELECT * FROM " + tableName + ";";
                db.execSQL(insertTableStringBuilder);
                printLog("[Table]" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
                printLog("[Generate temp table]" + tempTableName);
            } catch (SQLException e) {
                Log.e(TAG, "[Failed to generate temp table]" + tempTableName, e);
            }
        }
    }

    /**
     * 表是否存在
     *
     * @param db        数据库
     * @param isTemp    是否是临时表
     * @param tableName 表名
     * @return 是否存在
     */
    private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
        if (db == null || TextUtils.isEmpty(tableName)) {
            return false;
        }
        String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
        String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery(sql, new String[]{"table", tableName});
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }
            count = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count > 0;
    }

    /**
     * 获取列名
     *
     * @param daoConfig 数据库设置
     * @return 列名
     */
    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < daoConfig.allColumns.length; i++) {
            builder.append(daoConfig.allColumns[i]);
            builder.append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    /**
     * 删除表
     *
     * @param db         数据库
     * @param ifExists   如果存在(则删除)
     * @param daoClasses 表
     */
    @SafeVarargs
    private static void dropAllTables(Database db, boolean ifExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "dropTable", ifExists, daoClasses);
        printLog("[Drop all table by reflect]");
    }

    /**
     * /**
     * 创建表
     *
     * @param db          数据库
     * @param ifNotExists 如果不存在(则创建)
     * @param daoClasses  表
     */
    @SafeVarargs
    private static void createAllTables(Database db, boolean ifNotExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        reflectMethod(db, "createTable", ifNotExists, daoClasses);
        printLog("[Create all table by reflect]");
    }

    /**
     * dao class already define the sql exec method, so just invoke it
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    private static void reflectMethod(Database db, String methodName, boolean isExists, @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (daoClasses.length < 1) {
            return;
        }
        try {
            for (Class cls : daoClasses) {
                Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
                method.invoke(null, db, isExists);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复数据
     *
     * @param db         数据库
     * @param daoClasses 表
     */
    @SafeVarargs
    private static void restoreData(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
            DaoConfig daoConfig = new DaoConfig(db, daoClass);
            String tableName = daoConfig.tablename;
            String tempTableName = daoConfig.tablename.concat("_TEMP");

            if (!isTableExists(db, true, tempTableName)) {
                continue;
            }

            try {
                // get all columns from tempTable, take careful to use the columns list
                List<TableInfo> newTableInfos = TableInfo.getTableInfo(db, tableName);
                List<TableInfo> tempTableInfos = TableInfo.getTableInfo(db, tempTableName);
                ArrayList<String> selectColumns = new ArrayList<>(newTableInfos.size());
                ArrayList<String> intoColumns = new ArrayList<>(newTableInfos.size());
                for (TableInfo tableInfo : tempTableInfos) {
                    if (newTableInfos.contains(tableInfo)) {
                        String column = '`' + tableInfo.name + '`';
                        intoColumns.add(column);
                        selectColumns.add(column);
                    }
                }
                // NOT NULL columns list
                for (TableInfo tableInfo : newTableInfos) {
                    if (tableInfo.notnull && !tempTableInfos.contains(tableInfo)) {
                        String column = '`' + tableInfo.name + '`';
                        intoColumns.add(column);

                        String value;
                        if (tableInfo.dfltValue != null) {
                            value = "'" + tableInfo.dfltValue + "' AS ";
                        } else {
                            value = "'' AS ";
                        }
                        selectColumns.add(value + column);
                    }
                }

                if (intoColumns.size() != 0) {
                    String insertTableStringBuilder = "REPLACE INTO " + tableName + " (" +
                            TextUtils.join(",", intoColumns) +
                            ") SELECT " +
                            TextUtils.join(",", selectColumns) +
                            " FROM " + tempTableName + ";";
                    db.execSQL(insertTableStringBuilder);
                    printLog("[Restore data] to " + tableName);
                }
                db.execSQL("DROP TABLE " + tempTableName);
                printLog("[Drop temp table]" + tempTableName);
            } catch (SQLException e) {
                Log.e(TAG, "[Failed to restore data from temp table ]" + tempTableName, e);
            }
        }
    }

    /**
     * 获取列
     *
     * @param db        数据库
     * @param tableName 表名
     * @return 列
     */
    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (null == columns) {
                columns = new ArrayList<>();
            }
        }
        return columns;
    }

    /**
     * 输出日志
     *
     * @param info 信息
     */
    private static void printLog(String info) {
        if (DEBUG) {
            Log.d(TAG, info);
        }
    }

    /**
     * 表结构信息
     */
    private static class TableInfo {
        int cid;
        String name;
        String type;
        boolean notnull;
        String dfltValue;
        boolean pk;

        @Override
        public boolean equals(Object o) {
            return this == o
                    || o != null
                    && getClass() == o.getClass()
                    && name.equals(((TableInfo) o).name);
        }

        /**
         * 获取表信息
         *
         * @param db        数据库
         * @param tableName 表名
         * @return 表信息
         */
        private static List<TableInfo> getTableInfo(Database db, String tableName) {
            String sql = "PRAGMA table_info(" + tableName + ")";
            printLog(sql);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor == null) {
                return new ArrayList<>();
            }

            TableInfo tableInfo;
            List<TableInfo> tableInfos = new ArrayList<>();
            while (cursor.moveToNext()) {
                tableInfo = new TableInfo();
                tableInfo.cid = cursor.getInt(0);
                tableInfo.name = cursor.getString(1);
                tableInfo.type = cursor.getString(2);
                tableInfo.notnull = cursor.getInt(3) == 1;
                tableInfo.dfltValue = cursor.getString(4);
                tableInfo.pk = cursor.getInt(5) == 1;
                tableInfos.add(tableInfo);
                // printLog(tableName + "：" + tableInfo);
            }
            cursor.close();
            return tableInfos;
        }

        @Override
        public String toString() {
            return "TableInfo{" +
                    "cid=" + cid +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", notnull=" + notnull +
                    ", dfltValue='" + dfltValue + '\'' +
                    ", pk=" + pk +
                    '}';
        }
    }
}
