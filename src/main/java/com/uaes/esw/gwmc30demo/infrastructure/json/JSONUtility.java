package com.uaes.esw.gwmc30demo.infrastructure.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public interface JSONUtility {

    Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .create();

    static <T> T transferFromJSON2Object(String ObjectString, Class<T> obj){
        return gson.fromJson(ObjectString, obj);
    }

    static String transferFromObject2JSON(Object obj){
        return gson.toJson(obj);
    }

    static boolean isJSONValid(String jsonInString) {
        try {
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }

}
