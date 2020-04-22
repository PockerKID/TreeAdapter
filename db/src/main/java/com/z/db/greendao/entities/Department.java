package com.z.db.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Department
 *
 * @author KID
 * @date 2020/4/22.
 */
@Entity
public class Department implements Serializable {

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
    private String uuid;

    /**
     * 名字
     */
    private String name;

    /**
     * 名字搜索字段
     */
    private String nameSearchStr;

    /**
     * 父节点ID
     */
    private String parentId;

    @Generated(hash = 675677454)
    public Department(Long id, @NotNull String uuid, String name,
            String nameSearchStr, String parentId) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.nameSearchStr = nameSearchStr;
        this.parentId = parentId;
    }

    @Generated(hash = 355406289)
    public Department() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
