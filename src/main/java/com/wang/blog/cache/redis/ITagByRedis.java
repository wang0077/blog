package com.wang.blog.cache.redis;

import com.wang.blog.bean.Tag;

import java.util.List;

public interface ITagByRedis {

    /**
     * 通过List中的id获取标签实体
     * @param ids List的id
     * @return 返回对应的标签
     */
    List<Tag> getTagByBlog(List<Integer> ids);

    /**
     * 更新标签
     * @param id 标签的Id
     * @param tag 新的标签
     */
    void updateTag(Long id, Tag tag);

    /**
     * 删除标签
     * @param id 标签的Id
     */
    void deleteTag(Long id);

    /**
     * 获取Redis中的标签数量
     * @return 返回Redis的标签数量
     */
    Long countTag();

    /**
     * 按照分页规则查找标签
     * @param start 其实位置
     * @param end 结束位置
     * @return 返回当前分页的标签
     */
    List<Tag> getTagByPage(int start, int end);
    /**
     * 通过id从redis获取标签
     * @param tagId 标签的id
     * @return 返回标签
     */
    Tag getTagById(Long tagId);

    /**
     * 通过name从Redis获取标签
     * @param name 标签的名字
     * @return 返回标签
     */
    Tag getTagByName(String name);

    /**
     * 获取所有的标签
     * @return 返回所有的标签
     */
    List<Tag> listTag();

    /**
     * 将标签存入redis
     * @param tag 需要储存的Tag
     */
    void setTag(Tag tag);
}
