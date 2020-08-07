package com.wang.blog.Dao.admin;

import com.wang.blog.Bean.Tag;
import com.wang.blog.Bean.Type;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
//@Repository
public interface ITagDao {

    public Tag getTagByName(@Param("name") String name);

    public void saveTag(@Param("name") String name);

    public Tag getTagById(@Param("id") Long id);

    public List<Tag> listTag(@Param("start") int start, @Param("end") int end);

    public void updateTag(@Param("id") Long id,@Param("name") String name);

    public void deleteTag(@Param("id") Long id);

    public List<Tag> listTagByCount(@Param("start") int start,@Param("end") int end);

    public int getTagCount();


    public List<Tag> getTagAll();

    public List<Tag> searchTag(List<Integer> list);

    public List<Tag> getTagByBlogId(Long id);

    public void deleteTag_Blog(Long id);
}
