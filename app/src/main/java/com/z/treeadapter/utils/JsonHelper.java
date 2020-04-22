package com.z.treeadapter.utils;

import com.z.treeadapter.utils.json.FastJsonConverter;
import com.z.treeadapter.utils.json.GsonConverter;
import com.z.treeadapter.utils.json.JsonConverterType;
import com.z.treeadapter.utils.json.base.IJsonConverter;

import java.util.List;

/**
 * JsonHelper
 *
 * @author KID
 * @date 2020/4/22.
 */
public class JsonHelper {
    /**
     * TAG
     */
    private static final String TAG = JsonHelper.class.getSimpleName();

    /**
     * 单例对象
     */
    private static volatile JsonHelper singleton;

    /**
     * 构造函数
     */
    private JsonHelper() {
    }

    /**
     * @return 单例对象
     */
    public static JsonHelper getInstance() {
        if (singleton == null) {
            synchronized (JsonHelper.class) {
                if (singleton == null) {
                    singleton = new JsonHelper();
                }
            }
        }
        return singleton;
    }

    /**
     * JSON转换方式
     */
    private JsonConverterType jsonConverterType = JsonConverterType.FastJson;

    /**
     * JSON转换接口
     */
    private IJsonConverter iJsonConverter;

    /**
     * 获取JSON转换方式
     *
     * @return JSON转换方式
     */
    public JsonConverterType getJsonConverterType() {
        return jsonConverterType;
    }

    /**
     * 设置JSON转换方式
     *
     * @param jsonConverterType JSON转换方式
     */
    public void setJsonConverterType(JsonConverterType jsonConverterType) {
        this.jsonConverterType = jsonConverterType;
    }

    /**
     * @return JSON转换接口
     */
    public IJsonConverter getJsonConverter() {
        return getJsonConverter(jsonConverterType);
    }

    /**
     * 获取JSON转换接口
     *
     * @param converterType Json转换方式
     * @return JSON转换接口
     */
    public IJsonConverter getJsonConverter(JsonConverterType converterType) {
        if (iJsonConverter == null || jsonConverterType != converterType) {
            switch (converterType) {
                case FastJson:
                default:
                    iJsonConverter = new FastJsonConverter();
                    break;
                case Gson:
                    iJsonConverter = new GsonConverter();
                    break;
            }
            jsonConverterType = converterType;
        }
        return iJsonConverter;
    }

    /**
     * 将对象封装为JSON字符串
     *
     * @param object 需要封装的对象
     * @return JSON字符串
     */
    public String toJson(Object object) {
        return getJsonConverter().toJson(object);
    }

    /**
     * 将对象列表封装为JSON字符串
     *
     * @param object 需要封装的对象列表
     * @return JSON字符串
     */
    public <T> String toJson(List<T> object) {
        return getJsonConverter().toJson(object);
    }

    /**
     * 从JSON字符串串中提取需要的值
     *
     * @param jsonStr   JSON字符串
     * @param fieldName 字段名
     * @return 字段值
     */
    public String getValueFromJson(String jsonStr, String fieldName) {
        return getJsonConverter().getValueFromJson(jsonStr, fieldName);
    }

    /**
     * 将JSON字符串解析为指定类型的对象
     *
     * @param jsonString JSON字符串
     * @param classType  对象类型
     * @param <T>        类型
     * @return 对象
     */
    public <T> T parseJson(String jsonString, Class<T> classType) {
        return getJsonConverter().parseObject(jsonString, classType);
    }

    /**
     * 将JSON字符串解析为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz Class<T>
     * @param <T>   类型
     * @return 列表
     */
    public <T> List<T> jsonToList(String json, Class<T> clazz) {
        return getJsonConverter().jsonToList(json, clazz);
    }
}
