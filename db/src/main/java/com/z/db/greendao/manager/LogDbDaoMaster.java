package com.z.db.greendao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.z.db.greendao.MigrationHelper;
import com.z.db.greendao.dao.DaoMaster;
import com.z.db.greendao.dao.ErrorLogDao;

import org.greenrobot.greendao.database.Database;

/**
 * LogDbDaoMaster
 *
 * @author KID
 * @date 2020/4/22.
 */
public class LogDbDaoMaster extends DaoMaster {

    public LogDbDaoMaster(SQLiteDatabase db) {
        super(db);
    }

    public LogDbDaoMaster(Database db) {
        super(db);
    }

    /**
     * Creates underlying database table using DAOs.
     */
    public static void createAllTables(Database db, boolean ifNotExists) {
        ErrorLogDao.createTable(db, ifNotExists);
    }

    /**
     * Drops underlying database table using DAOs.
     */
    public static void dropAllTables(Database db, boolean ifExists) {
        ErrorLogDao.dropTable(db, ifExists);
    }

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    dropAllTables(db, ifExists);

                }
            }, ErrorLogDao.class);
        }
    }
}