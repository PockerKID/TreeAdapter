package com.z.db.greendao;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * GreenDaoContext
 *
 * @author KID
 * @date 2020/4/22.
 */
public class GreenDaoContext extends ContextWrapper {
    /**
     * TAG
     */
    private static final String TAG = GreenDaoContext.class.getSimpleName();

    /**
     * 数据库路径
     */
    private String dbDir;

    /**
     * 构造函数
     *
     * @param context {@link Context}
     */
    public GreenDaoContext(Context context) {
        super(context);
    }

    /**
     * 设置数据库路径
     *
     * @param dbDir 数据库路径
     */
    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName 数据库名称
     */
    @Override
    public File getDatabasePath(String dbName) {
        if (TextUtils.isEmpty(dbDir)) {
            return super.getDatabasePath(dbName);
        }
        File baseFile = new File(dbDir);
        // 目录不存在则自动创建目录
        final boolean createResult;
        if (!baseFile.exists()) {
            createResult = baseFile.mkdirs();
        } else {
            createResult = true;
        }
        if (createResult) {
            // 数据库路径
            String dbPath = dbDir + File.separator + dbName;
            // 数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            // 判断文件是否存在，不存在则创建该文件
            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                try {
                    // 创建文件
                    isFileCreateSuccess = dbFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;
            }
            // 返回数据库文件对象
            if (isFileCreateSuccess) {
                return dbFile;
            } else {
                return super.getDatabasePath(dbName);
            }
        } else {
            return super.getDatabasePath(dbName);
        }
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name    数据库名称
     * @param mode    Operating mode
     * @param factory {@link  SQLiteDatabase.CursorFactory}
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name         数据库名称
     * @param mode         Operating mode
     * @param factory      {@link  SQLiteDatabase.CursorFactory}
     * @param errorHandler {@link ContextWrapper#openOrCreateDatabase(String, int, SQLiteDatabase.CursorFactory, DatabaseErrorHandler)}
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
    }
}