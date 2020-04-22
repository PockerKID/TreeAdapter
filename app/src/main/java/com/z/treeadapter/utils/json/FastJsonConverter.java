package com.z.treeadapter.utils.json;

import com.alibaba.fastjson.JSON;
import com.z.treeadapter.utils.json.base.BaseConverter;

import java.util.List;

/**
 * FastJsonConverter
 *
 * @author KID
 * @date 2020/4/22.
 */
public class FastJsonConverter extends BaseConverter {
    @Override
    public String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> String toJson(List<T> object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T parseObject(String jsonString, Class<T> classType) {
        return JSON.parseObject(jsonString, classType);
    }

    @Override
    public <T> List<T> jsonToList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
