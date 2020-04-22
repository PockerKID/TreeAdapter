package com.z.db.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * ErrorLog
 *
 * @author KID
 * @date 2020/4/22.
 */
@Entity
public class ErrorLog {
    /**
     * 数据库自增ID
     */
    @Id(autoincrement = true)
    private Long id;

    /**
     * 仪器号
     */
    private String deviceNumber;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 软件版本
     */
    private String appVersion;

    /**
     * 时间
     */
    private long time;

    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * 类TAG
     */
    private String classTag;

    /**
     * 方法TAG
     */
    private String functionTag;

    @Generated(hash = 1144558188)
    public ErrorLog(Long id, String deviceNumber, String deviceType,
            String osVersion, String appVersion, long time, String errorInfo,
            String classTag, String functionTag) {
        this.id = id;
        this.deviceNumber = deviceNumber;
        this.deviceType = deviceType;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.time = time;
        this.errorInfo = errorInfo;
        this.classTag = classTag;
        this.functionTag = functionTag;
    }

    @Generated(hash = 1694956548)
    public ErrorLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceNumber() {
        return this.deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getClassTag() {
        return this.classTag;
    }

    public void setClassTag(String classTag) {
        this.classTag = classTag;
    }

    public String getFunctionTag() {
        return this.functionTag;
    }

    public void setFunctionTag(String functionTag) {
        this.functionTag = functionTag;
    }
}
