package com.z.treeadapter.utils.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.z.treeadapter.utils.json.base.BaseConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * GsonConverter
 *
 * @author KID
 * @date 2020/4/22.
 */
public class GsonConverter extends BaseConverter {

    @Override
    public String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public <T> String toJson(List<T> object) {
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(object, new TypeToken<List<T>>() {
        }.getType());
        return gson.toJson(element);
    }

    @Override
    public <T> T parseObject(String jsonString, Class<T> classType) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, classType);
    }

    @Override
    public <T> List<T> jsonToList(String json, Class<T> clazz) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        List<JsonObject> jsonObjectList;
        try {
            jsonObjectList = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObjectList = new ArrayList<>();
        }
        List<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjectList) {
            try {
                arrayList.add(gson.fromJson(jsonObject, clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }
}