package com.wang.blog.cache.redis;

import com.wang.blog.bean.Type;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ITypeByRedis {
    /**
     * 通过id从redis获取分类
     * @param typeId 分类的id
     * @return 返回分类
     */
    Type getTypeById(int typeId);

    /**
     * 通过name从Redis获取分类
     * @param name 分类的名字
     * @return 返回分类
     */
    Type getTypeByName(String name);

    /**
     * 获取所有的分类
     * @return 返回所有的分类
     */
    List<Type> listType();

    /**
     * 将分类存入redis
     * @param type 需要储存的type
     * @return 返回是否操作成功
     */
    boolean setType(Type type);
}
