package com.z.treeadapter.utils.json;

/**
 * JsonConverterType
 *
 * @author KID
 * @date 2020/4/22.
 */
public enum  JsonConverterType {
    /**
     * Google Gson
     * 对大小写敏感,区分字段Key的大小写
     */
    Gson,
    /**
     * Ali FastJson
     * 对大小写不敏感,不区分字段Key的大小写,但自定义对象必须有默认的无参构造函数
     */
    FastJson
}
