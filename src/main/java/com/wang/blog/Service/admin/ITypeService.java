package com.wang.blog.service.admin;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Type;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ITypeService {

    /**
     * 新增分类
     * @param name 分类名字
     */
    void saveType(String name);

    /**
     * 通过分类的Id获取分类
     * @param id 分类的Id
     * @return 查询到的分类
     */
    Type getType(Long id);

    /**
     * 获取分类所拥有的博客数
     * @return List形式type,type内有count记录博客数
     */
    List<Type> lisTypeByCount();

    /**
     * 获取所有分类
     * @param page 分页
     * @see ITypeService#listType() 这个是不做分页处理的获取所有的饿分类
     * @return 分页完成后的当前页面的分类
     */
    Page<Type> listType(Page<Type> page);

    /**
     * 获取分类的数量
     * @return 返回分类的数量
     */
    int countType();

    /**
     * 更新分类
     * @param id 分类的Id
     * @param name 需要更新的分类名字
     */
    void updateType(Long id,String name);

    /**
     * 删除分类
     * @param id 分类的Id
     */
    void deleteType(Long id);

    /**
     * 通过名字获取分类
     * @param name 分类的名字
     * @return 搜索得到的分类
     */
    Type getTypeByName(String name);

    /**
     * 获取所有的分类,不做分页处理
     * @see ITypeService#listType(Page) 这是分页处理的获取所有分类
     * @return 所有的分类
     */
    List<Type> listType();
}
