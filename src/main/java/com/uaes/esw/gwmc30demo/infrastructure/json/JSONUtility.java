package com.uaes.esw.gwmc30demo.infrastructure.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public interface JSONUtility {

    Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .create();

    public static <T> T transferFromJSON2Object(String ObjectString, Class<T> obj){
        return gson.fromJson(ObjectString, obj);
    }

    public static String transferFromObject2JSON(Object obj){
        return gson.toJson(obj);
    }

}
