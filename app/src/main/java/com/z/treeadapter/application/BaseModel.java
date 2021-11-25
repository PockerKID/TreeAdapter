package com.z.treeadapter.application;

import androidx.annotation.NonNull;

import com.z.treeadapter.utils.JsonHelper;

import java.io.Serializable;

/**
 * BaseModel
 *
 * @author KID
 * @date 2020/4/22.
 */
public class BaseModel implements Serializable {
    public BaseModel() {
    }

    @NonNull
    @Override
    public String toString() {
        return JsonHelper.getInstance().toJson(this);
    }
}
