package com.test.utils;

import org.json.JSONObject;


public class JSONParser {
    /**
     * Parser the json内容
     *
     * @param JsonString String
     * @param JsonId     String
     * @return JsonValue String
     */
    public static String getJsonValue(String JsonString, String JsonId) {
        String JsonValue = "";
        if (JsonString == null || JsonString.trim().length() < 1) {
            return null;
        }
        try {
            JSONObject obj1 = new JSONObject(JsonString);
            JsonValue = obj1.getString(JsonId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonValue;
    }
}
