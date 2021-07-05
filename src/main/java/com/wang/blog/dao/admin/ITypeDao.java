package com.wang.blog.dao.admin;

import com.wang.blog.bean.Type;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author wangsiyuan
 */
@Repository
public interface ITypeDao {

    /**
     * 通过名字获取分类
     * @param name 分类名字
     * @return 返回得到分类
     */
    Type getTypeByName(@Param("name") String name);

    /**
     * 新建新的分类
     * @param name 存入的名字
     */
    void saveType(@Param("name") String name);

    /**
     * 通过Id获取分类
     * @param id 分类的Id
     * @return 返回得到的分类
     */
    Type getTypeById(@Param("id") Long id);

    /**
     * 获取区间范围的分类
     * @param start 起始位置
     * @param end 结束位置
     * @return 区间[start,end]的分类
     */
    List<Type> listType(@Param("start") int start, @Param("end") int end);

    /**
     * 获取[start,end]区间的分类所拥有的博客数
     * @param start 起始位置
     * @param end 结束位置
     * @return 获取[start,end]区间的分类所拥有的博客数
     */
    List<Type> listTypeByCountBlog(@Param("start") int start, @Param("end") int end);

    /**
     * 更新分类
     * @param id 分类的Id
     * @param name 替换分类的名字
     */
    void updateType(@Param("id") Long id,@Param("name") String name);

    /**
     * 删除分类
     * @param id 分类的Id
     */
    void deleteType(@Param("id") Long id);

    /**
     *  获取分类个数
     * @return 返回分类的个数
     */
    int countType();

    /**
     *  获取所有的分类
     * @return 返回所有的分类
     */
    List<Type> listTypeAll();
}
