package com.z.treeadapter.utils.json.base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * BaseConverter
 *
 * @author KID
 * @date 2020/4/22.
 */
public abstract class BaseConverter implements IJsonConverter {

    @Override
    public String getValueFromJson(String jsonStr, String fieldName) {
        try {
            JSONObject object = new JSONObject(jsonStr);
            return object.getString(fieldName);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
