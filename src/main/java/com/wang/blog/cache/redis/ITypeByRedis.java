package com.wang.blog.cache.redis;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Type;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ITypeByRedis {

    /**
     * 更新分类
     * @param id 分类的Id
     * @param type 新的分类
     */
    void updateType(Long id,Type type);

    /**
     * 删除分类
     * @param id 分类的Id
     */
    void deleteType(Long id);

    /**
     * 获取Redis中的分类数量
     * @return 返回Redis的分类数量
     */
    Long countType();

    /**
     * 按照分页规则查找分类
     * @param start 其实位置
     * @param end 结束位置
     * @return 返回当前分页的分类
     */
    List<Type> getTypeByPage(int start,int end);
    /**
     * 通过id从redis获取分类
     * @param typeId 分类的id
     * @return 返回分类
     */
    Type getTypeById(Long typeId);

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
    void setType(Type type);
}
