package com.wang.blog.cache.redis;

import com.wang.blog.bean.Blog;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface IBlogByRedis {

    /**
     *  获取所有的博客
     * @return 返回所有博客
     */
    List<Blog> listBlog();

    /**
     * 新增博客
     * @param blog 博客
     */
    void saveBlog(Blog blog);

    /**
     * 通过Id获取博客
     * @param id 博客Id
     * @return 返回搜索得到的id
     */
    Blog getBlogById(Long id);

    /**
     *  通过Id获取博客(重载)
     *  @param id 博客Id
     *  @return 返回搜索得到的id
     */
    Blog getBlogById(Integer id);

    /**
     * 更新对应博客的浏览量
     * @param id 博客的id
     */
    void updateView(Long id);

    /**
     *  通过标签的Id获取Blog
     * @param tagId 标签的Id
     * @return 返回博客列表
     */
    List<Blog> listBlogByTag(Long tagId);



    /**
     * 按照更新时间排序获取博客
     * @return 返回正序排列
     */
    List<Blog> listBlogWithUpTime();

    /**
     *  获取博客的数量
     * @return 返回博客的数量
     */
    int countByBlog();

    /**
     *  更新博客
     * @param blog 更新后的博客
     */
    void updateBlog(Blog blog);

    /**
     *  删除博客
     * @param blogId 博客的Id
     */
    void delBlog(Long blogId);


    /**
     * 获取博客年份
     * @return 返回博客年份
     */
    List<String> getYear();

    /**
     * 按照年份查找博客
     * @param year 年份
     * @return 返回当前年份的所有博客
     */
    List<Blog> getBlogByYear(String year);


    /**
     *  按照分页进行区间查找
     * @param start 起始位置
     * @param length 截取长度
     * @return 返回区间Blog
     */
    List<Blog> listBlogOnPage(int start,int length);

    /**
     * 通过自己传入的数组进行分页区间查找
     * @param start 起始区间
     * @param length 区间长度
     * @param blogs 需要分页的数组
     * @return 返回分页完成的数组
     */
    List<Blog> listBlogOnPage(int start,int length,List<Blog> blogs);

    /**
     * 增加博客的计数器
     */
    void addSize();

    /**
     * 减少博客的计数器
     */
    void decSize();

    /**
     * 按照时间排序再进行分页查找
     * @see #listBlogOnPage(int, int) 逻辑与这个方法相同，只是换一个名字有辨识度
     * @param start 起始位置
     * @param length 截取长度
     * @return 返回排序后的分页博客内容
     */
    List<Blog> listBlogWithUpTimeOnPage(int start,int length);

    /**
     *  拥有这个标签的博客数
     * @param tid 标签的ID
     * @return 返回博客数
     */
    int countBlogByTag(Long tid);

    /**
     *  获取拥有这个分类的博客
     * @param tid 分类标签
     * @return 返回博客列表
     */
    List<Blog> listBlogByType(Long tid);

    /**
     *  拥有这个分类的博客数
     * @param tid 分类Id
     * @return 返回博客数
     */
    int countBlogByType(Long tid);
}
