package com.wang.blog.util;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author wangsiyuan
 */
public class BeanAndMap {

    public static HashMap<String,String> toMap(Object object){
        Class<?> aClass = object.getClass();
        Field[] fields = aClass.getDeclaredFields();
        int len = fields.length;
        HashMap<String, String> hashMap = new HashMap<>(len);
        for(Field f : fields){
            f.setAccessible(true);
            try {
                hashMap.put(f.getName(),f.get(object).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }
}
