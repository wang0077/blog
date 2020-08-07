package com.wang.blog.Service.admin;

import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Tag;
import com.wang.blog.Bean.Type;

import java.util.List;

public interface ITagService {

    public void saveTag(String name);

    public Tag getTagById(Long id);

    public Tag getTagByName(String name);

    public Page<Tag> listTag(Page<Tag> page);

    public List<Tag> listTagByCount();

    public int getTagCount();

    public void updateTag(Long id,String name);

    public void deleteTag(Long id);

    public List<Tag> listTag();

    public List<Tag> getTagSearch(String ids);

    public List<Tag> getTagByBlogId(Long id);
}
