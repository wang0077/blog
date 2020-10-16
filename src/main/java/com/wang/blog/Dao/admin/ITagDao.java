package com.wang.blog.dao.admin;

import com.wang.blog.bean.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * @author wangsiyuan 
 */
//@Mapper
@Repository
public interface ITagDao {

    /**
     * 通过名字进行搜索标签
     * @param name 需要搜索的标签名字
     * @return 搜索得到标签
     */
    Tag getTagByName(@Param("name") String name);

    /**
     * 新建标签
     * @param name 标签的名字
     */
    void saveTag(@Param("name") String name);

    /**
     * 通过Id进行搜索标签
     * @param id 标签的Id
     * @return 搜索得到的标签
     */
    Tag getTagById(@Param("id") Long id);

    /**
     * 分页功能的标签搜索，通过SQL的limit方言进行区间搜索
     * @param start 起始位置
     * @param end   结束位置
     * @return 返回[start,end]区间内的标签
     */
    List<Tag> listTag(@Param("start") int start, @Param("end") int end);

    /**
     * 更新标签
     * @param id 需要更新标签的Id
     * @param name 替换标签的名字
     */
    void updateTag(@Param("id") Long id,@Param("name") String name);

    /**
     * 删除标签
     * @param id 需要删除标签的Id
     */
    void deleteTag(@Param("id") Long id);

    /**
     * 获取使用各种标签的博客数量
     * @param start 起始位置
     * @param end   结束位置
     * @return 返回各个标签对应的博客数量的List
     */
    List<Tag> countTagByBlog(@Param("start") int start, @Param("end") int end);

    /**
     * 获取标签的数量
     * @return 标签数量
     */
    int countTag();

    /**
     * 获取所有的标签
     * @return 所有的标签
     */
    List<Tag> listTagAll();

    /**
     * 新增博客时获取标签实体
     * @param list 前端传入的新增博客的标签Id
     * @return 返回标签实体
     */
    List<Tag> getTagByBlog(List<Integer> list);

    /**
     * 获取已经存在博客的标签
     * @param id 当前博客的Id
     * @return 返回得到的标签
     */
    List<Tag> getTagByBlogId(Long id);

    /**
     * 删除标签
     * @param id 标签的Id
     */
    void deleteTagBlog(Long id);
}
