package com.wang.blog.util;

import com.alibaba.fastjson.JSONObject;
import org.jetbrains.annotations.NotNull;

/**
 * @author wangsiyuan
 */
public class JsonUtil {
    public static <T> T json2Bean(String json, @NotNull T type){
        return (T)JSONObject.parseObject(json, type.getClass());
    }
}
