package com.wang.blog.service.admin;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author wangsiyuan
 */
public interface IBlogService {

    /**
     *  获取标签的Id
     * @param tags 该博客的所有标签
     * @return 返回标签的id,String类型
     */
    String getTagIds(List<Tag> tags);

    /**
     * 通过Id获取博客
     * @param id 博客的Id
     * @return 返回博客的实体
     */
    Blog getBlog(Long id);

    /**
     * 将博客的内容转换为MarkDown格式
     * @param id 博客Id
     * @return 返回MarkDown格式的Content的博客
     */
    Blog getMarkDownBlog(Long id);

    /**
     *  更新浏览量
     * @param id 该博客的Id
     */
    void updateView(Long id);

    /**
     * 前台通过标题或内容进行搜索,只需要传入String类型的数据
     * @param page 分页
     * @param query 搜索内容
     * @return 返回搜素得到的结果
     */
    Page<Blog> searchBlogByString(Page<Blog> page, String query);

    /**
     * 后台的搜索,因为涉及到分类,推荐的选项,传递的是Blog实体,用来存储标题,分类,和推荐等信息
     * @param page 分页
     * @param blog 搜索博客的具体信息
     * @return 返回搜索得到的博客
     */
    Page<Blog> listBlog(Page<Blog> page, Blog blog);

    /**
     * 通过分页功能,用标签搜索得到博客
     * @param page 分页
     * @param tagId 标签Id
     * @return 返回得到具有该标签的博客
     */
    Page<Blog> listBlogByTag(Page<Blog> page,Long tagId);

    /**
     * 通过分页获取博客
     * @param page 分页
     * @return 得到分页后的博客
     */
    Page<Blog> listBlog(Page<Blog> page);

    /**
     * 获取最新的N篇博客
     * @return 返回最新的博客
     */
    List<Blog> listBlogByTime();

//    public List<Blog> listBlogByFoot();

    /**
     * 获取博客的总数量
     * @return 博客的数量
     */
    int getBlogCount();

    /**
     * 通过分页功能获取博客信息
     * @param page 分页数据
     * @return 通过分页得到的指定数量的博客
     */
    List<Blog> getBlogOnPage(Page<Blog> page);

    /**
     * 新增博客
     * @param blog 博客的具体信息(Type也包含在里面)
     * @param tags 该博客的标签信息
     * @return 返回博客(Controller需要用Blog进行判空,用来判断是否操作成功)
     */
    Blog saveBlog(Blog blog,List<Tag> tags);

//    public Date format_toString(Date time);

//    public Date format_toDate(String time);

    /**
     * 更新博客
     * @param id 博客的Id
     * @param blog 博客的信息
     */
    void updateBlog(Long id,Blog blog);

    /**
     * 删除博客
     * @param id 博客的Id
     */
    void deleteBlog(Long id);

    /**
     * 删除评论
     * @param id 博客Id
     */
    void deleteComment(Long id);

    /**
     * 将博客按年份进行分类
     * @return 返回分类完的博客 Map<年份,List<博客>>
     */
    Map<String,List<Blog>> listBlogByYear();
}
