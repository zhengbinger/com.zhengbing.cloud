package com.zhengbing.cloud.starter.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Gson 工具类
 *
 * @author zhengbing_vendor
 * @date 2020/6/2
 **/
public class GsonUtils {

    private GsonUtils() {
    }

    /**
     * 将任务Java对象转换为json字符串String
     *
     * @param object Object
     * @return String json
     */
    public static String toJsonString(Object object) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(object);
    }

    /**
     * 将json字符串转换成JAVA对象
     *
     * @param json  json字符串
     * @param clazz 需要转换对象的Class实例
     * @return Object
     */
    public static <T> T fromJson(String json, Class<T> clazz) {

        return createGson().fromJson(json, clazz);
    }

    /**
     * 将json字符串转换未指定类型集合
     *
     * @param json json字符串
     * @param type Type(指定转换类型)
     * @return Object
     */
    public static <T> T fromJson(String json, Type type) {
        return createGson().fromJson(json, type);
    }

    /**
     * 创建Gson 实例
     *
     * @return Gson
     */
    private static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return builder.create();
    }
}
