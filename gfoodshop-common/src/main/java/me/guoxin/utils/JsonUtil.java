package me.guoxin.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public static ObjectMapper objectMapper;

    /**
     * Java Bean转 Json字符串
     * @param object
     * @return
     */
    public static String toJSonString(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
