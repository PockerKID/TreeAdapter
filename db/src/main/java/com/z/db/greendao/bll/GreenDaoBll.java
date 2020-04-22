package com.z.db.greendao.bll;

import com.z.db.base.bll.BaseBll;
import com.z.db.greendao.dal.GreenDaoDal;

/**
 * GreenDaoBll
 *
 * @author KID
 * @date 2020/4/22.
 */
public class GreenDaoBll<T> extends BaseBll<T> {
    /**
     * 初始化-构造函数
     *
     * @param dal dal对象
     */
    public GreenDaoBll(GreenDaoDal<T> dal) {
        super(dal);
    }

    @Override
    public GreenDaoDal<T> getDal() {
        return (GreenDaoDal<T>) dal;
    }
}

