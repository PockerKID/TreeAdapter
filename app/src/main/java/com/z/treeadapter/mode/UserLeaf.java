package com.z.treeadapter.mode;

import com.z.db.greendao.entities.User;
import com.z.treeadapter.tree.model.BaseLeaf;

/**
 * UserLeaf
 *
 * @author KID
 * @date 2020/4/22.
 */
public class UserLeaf  extends BaseLeaf {

    /**
     * 用户
     */
    private User user;

    /**
     * 构造函数
     */
    public UserLeaf() {
    }

    /**
     * 构造函数
     * @param user  用户
     */
    public UserLeaf(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}