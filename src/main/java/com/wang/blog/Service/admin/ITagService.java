package com.wang.blog.service.admin;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;

import java.util.List;

public interface ITagService {

    public void saveTag(String name);

    public Tag getTagById(Long id);

    public Tag getTagByName(String name);

    public Page<Tag> listTag(Page<Tag> page);

    public List<Tag> countListByTag();

    public int getTagCount();

    public void updateTag(Long id,String name);

    public void deleteTag(Long id);

    public List<Tag> listTag();

    public List<Tag> getTagSearch(String ids);

    public List<Tag> getTagByBlogId(Long id);
}
