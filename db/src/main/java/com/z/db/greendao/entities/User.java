package com.z.db.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * User
 *
 * @author KID
 * @date 2020/4/22.
 */
@Entity
public class User implements Serializable {
    /**
     * GreenDao序列化需添加类似语句
     */
    private static final long serialVersionUID = 0L;

    /**
     * 数据库自增ID
     */
    @Id(autoincrement = true)
    private Long id;

    /**
     * UUID
     */
    @Index(unique = true)
    @NotNull
    private String userId;

    /**
     * 名字
     */
    private String name;

    /**
     * 显示名称(拼音)
     */
    private String nameSearchStr = "";

    /**
     * 部门ID
     */
    private String departmentId = "";

    @Generated(hash = 1555243214)
    public User(Long id, @NotNull String userId, String name, String nameSearchStr,
            String departmentId) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nameSearchStr = nameSearchStr;
        this.departmentId = departmentId;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSearchStr() {
        return this.nameSearchStr;
    }

    public void setNameSearchStr(String nameSearchStr) {
        this.nameSearchStr = nameSearchStr;
    }

    public String getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
}
