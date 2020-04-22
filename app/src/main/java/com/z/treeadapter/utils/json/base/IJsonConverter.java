package com.z.treeadapter.utils.json.base;

import java.util.List;

/**
 * IJsonConverter
 *
 * @author KID
 * @date 2020/4/22.
 */
public interface IJsonConverter {
    /**
     * 将对象封装为JSON字符串
     *
     * @param object 需要封装的对象
     * @return JSON字符串
     */
    String toJson(Object object);

    /**
     * 将对象列表封装为JSON字符串
     *
     * @param object 需要封装的对象列表
     * @return JSON字符串
     */
    <T> String toJson(List<T> object);

    /**
     * 从JSON字符串串中提取需要的值
     *
     * @param jsonStr   JSON字符串
     * @param fieldName 字段名
     * @return 字段值
     */
    String getValueFromJson(String jsonStr, String fieldName);

    /**
     * 将JSON字符串解析为指定类型的对象
     *
     * @param jsonString JSON字符串
     * @param classType  对象类型
     * @param <T>        类型
     * @return 对象
     */
    <T> T parseObject(String jsonString, Class<T> classType);

    /**
     * 将JSON字符串解析为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz Class<T>
     * @param <T>   类型
     * @return 列表
     */
    <T> List<T> jsonToList(String json, Class<T> clazz);
}