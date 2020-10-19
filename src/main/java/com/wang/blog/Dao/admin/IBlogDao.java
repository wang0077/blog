package com.wang.blog.dao.admin;

import com.wang.blog.bean.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author wangsiyuan
 */
@Repository
public interface IBlogDao {

    /**
     * 新增博客的时候，对博客和标签的中间表进行插入操作
     * @param bid 博客的Id
     * @param tid 标签的Id
     */
    void saveBlogTag(@Param("bid") Long bid, @Param("tid") Long tid);

    /**
     *  通过标题搜索博客
     * @param title 博客的标题
     * @return 返回对应的博客
     */
    Blog getBlogByTitle(@Param("title") String title);

    /**
     * 通过id获取博客
     * @param id 博客的Id
     * @return 返回博客
     */
    Blog getBlogById(Long id);

    /**
     * 统计博客数量
     * @return 返回博客数量
     */
    int countBlog();

    /**
     * 通过Id获取博客，并且获得该博客的标签信息
     * @param start 起始位置
     * @param end 结束为止
     * @param tid 博客Id
     * @return 返回博客内容(包括标签信息)
     */
    List<Blog> listTypeIncludeBlog(@Param("start") int start, @Param("end") int end, @Param("tid") Long tid);

    /**
     * 当前分类所拥有的博客数
     * @param blog 博客内容
     * @see com.wang.blog.dao.admin.IBlogDao#listBlog(int, int, Blog) 这个方法得到的是搜索结果，而本方法是得到搜索结果的数量
     * @return 返回分类拥有的博客数
     */
    int countTypeIncludeBlog(@Param("blog") Blog blog);

    /**
     *  通过标签的Id获取博客的内容(包括分类的信息)
     * @param tid 标签的Id
     * @return 返回博客的信息(包括分类的信息)
     */
    int countBlogByTag(@Param("tid") Long tid);

    /**
     *  获取最新时间的五篇公开的博客
     * @return 返回五篇最新的博客
     */
    List<Blog> listBlogByUpTime();

//    List<Blog> listBlogByUpTimeFoot();

    /**
     *  获取当前分类所有的博客
     * @param start 起始位置
     * @param end 结束位置
     * @param blog 博客内容
     * @return 返回当前分类的博客
     */
    List<Blog> listBlog(@Param("start") int start, @Param("end") int end, @Param("blog") Blog blog);

//    int getBlogCount();

    /**
     *  更新博客的浏览量
     * @param id 博客的Id
     */
    void updateView(Long id);

    /**
     *  获取区间博客的内容，并且将博客所属的分类也放入Blog实体中
     * @param start 起始位置
     * @param end 结束位置
     * @return 返回[start,end]博客的实体(包含type信息)
     */
    List<Blog> listBlogWithType(@Param("start") int start, @Param("end") int end);

//    List<Blog> SearchBlog(@Param("start") int start, @Param("end") int end, @Param("blog") Blog blog);

    /**
     *  按照倒序在规定区间范围查询博客信息(包括分类)
     * @param start 起始位置
     * @param end 结束位置
     * @return 返回排序完的博客信息(包括分类信息)
     */
    List<Blog> listBlogByTime(@Param("start") int start, @Param("end") int end);

    /**
     * 新增博客
     * @param blog 博客内容
     */
    void saveBlog(@Param("blog") Blog blog);

    /**
     * 更新博客内容
     * @param id 博客id
     * @param blog 博客内容
     */
    void updateBlog(@Param("id") Long id, @Param("blog") Blog blog);

    /**
     * 删除博客
     * @param id 博客id
     */
    void deleteBlog(Long id);

//    int getCount(@Param("blog") Blog blog);

    /**
     *  获取博客浏览量
     * @param id 博客id
     * @return 当前博客的浏览量
     */
    int getView(Long id);

    /**
     * 获取数据库中的所有年份并去重
     * @return 返回List 年份
     */
    List<String> getYear();

    /**
     *  获取传入年份的博客信息
     * @param year 年份
     * @return 返回传入年份的博客信息
     */
    List<Blog> getBlogByYear(String year);

    /**
     * 获取博客的创建时间
     * @param id 博客id
     * @return 当前博客的创建时间
     */
    Date getCreatTime(Long id);

    /**
     * 通过query进行对博客的标题和内容进行查询，得到的结果按照区间范围获取，并且获取分类信息
     * @param start 起始位置
     * @param end 結束位置
     * @param query 查詢的內容
     * @return 返回查询结果的区间[start,end]范围博客内容(包括分类信息)
     */
    List<Blog> searchBlog(@Param("start") int start, @Param("end") int end,String query);

    /**
     * 获取搜索结果的数量
     * @param query 搜索内容
     * @return 返回搜索得到的数量
     */
    int countSearch(String query);
}
