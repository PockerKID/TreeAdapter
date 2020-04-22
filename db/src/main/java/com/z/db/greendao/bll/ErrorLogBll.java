package com.z.db.greendao.bll;

import com.z.db.greendao.dal.GreenDaoDal;
import com.z.db.greendao.entities.ErrorLog;

/**
 * ErrorLogBll
 *
 * @author KID
 * @date 2020/4/22.
 */
public class ErrorLogBll extends GreenDaoBll<ErrorLog> {
    /**
     * 初始化-构造函数
     *
     * @param dal dal对象
     */
    public ErrorLogBll(GreenDaoDal<ErrorLog> dal) {
        super(dal);
    }
}
