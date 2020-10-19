package com.wang.blog.service.admin;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ITagService {

    /**
     * 保存标签
     * @param name 标签名字
     */
    public void saveTag(String name);

    /**
     * 通过Id获取标签
     * @param id 标签的Id
     * @return 返回获取的标签实体
     */
    public Tag getTagById(Long id);

    /**
     * 通过名字获取标签
     * @param name 标签名字
     * @return 返回获取的标签实体
     */
    public Tag getTagByName(String name);

    /**
     * 获取分页情况下的标签
     * @param page 分页Bean
     * @return 返回当前页面的标签
     */
    public Page<Tag> listTag(Page<Tag> page);

    /**
     * 获取使用该标签的博客数量
     * @return 返回数量
     */
    public List<Tag> countListByTag();

    /**
     * 获取标签的数量
     * @return 返回数量
     */
    public int getTagCount();

    /**
     * 更新标签
     * @param id 标签Id
     * @param name 标签替换的名字
     */
    public void updateTag(Long id,String name);

    /**
     * 删除标签
     * @param id 标签Id
     */
    public void deleteTag(Long id);

    /**
     * 获取所有的标签
     * @return 返回所有的标签
     */
    public List<Tag> listTag();

    /**
     * 通过前端传递的String类型的标签数据
     * @param ids String类型的标签数据 "1,2,3,4" 格式,需要对数据预处理
     * @return 返回得到的标签
     */
    public List<Tag> getTagSearch(String ids);

    /**
     *获取该博客的所有标签
     * @param id 该博客的Id
     * @return 返回所有的标签
     */
    public List<Tag> getTagByBlogId(Long id);
}
