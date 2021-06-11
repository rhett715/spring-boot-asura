package org.rhett.admin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author Rhett
 * @Date 2021/6/11
 * @Description
 * Jackson工具类
 */
public class JacksonUtil {
    private static final ObjectMapper JSON = new ObjectMapper();

    /**
     * 对象转为json字符串
     * @param obj 对象
     * @return json字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return JSON.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转为对象
     * @param json 字符串
     * @param classZ 对象
     * @param <T> 对象类型
     * @return 目标对象
     */
    public static <T> T toObject(String json, Class<T> classZ) {
        try {
            return JSON.readValue(json, classZ);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
