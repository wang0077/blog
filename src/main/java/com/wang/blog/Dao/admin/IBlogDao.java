package com.wang.blog.Dao.admin;

import com.wang.blog.Bean.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Mapper
//@Repository
public interface IBlogDao {

    void saveBlog_Tag(@Param("bid") Long bid, @Param("tid") Long tid);

    Blog getByTitle(@Param("title") String title);

    Blog getBlog(Long id);

    int getCountAll();

    List<Blog> listBlogAllByTag(@Param("start") int start, @Param("end") int end, @Param("tid") Long tid);

    int getCountBlogAllByType(@Param("blog") Blog blog);

    int getCountBlogAllByTag(@Param("tid") Long tid);

    List<Blog> listBlogByUpTime();

    List<Blog> listBlogByUpTimeFoot();

    List<Blog> listBlogAll(@Param("start") int start, @Param("end") int end, @Param("blog") Blog blog);

    int getBlogCount();

    void UpdateView(Long id);

    List<Blog> getBlogAll(@Param("start") int start, @Param("end") int end);

    List<Blog> listBlog(@Param("start") int start, @Param("end") int end, @Param("blog") Blog blog);

    List<Blog> listBlogByTime(@Param("start") int start, @Param("end") int end);

    void saveBlog(@Param("blog") Blog blog);

    void updateBlog(@Param("id") Long id, @Param("blog") Blog blog);

    void deleteBlog(Long id);

    int getCount(@Param("blog") Blog blog);

    int getView(Long id);

    List<String> getYear();

    List<Blog> getBlogByYear(String year);

    Date getCreat_Time(Long id);

    List<Blog> searchBlog(@Param("start") int start, @Param("end") int end,String query);

    int searchCount(String query);
}
