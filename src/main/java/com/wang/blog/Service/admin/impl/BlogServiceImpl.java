package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.*;
import com.wang.blog.cache.redis.*;
import com.wang.blog.dao.ICommentDao;
import com.wang.blog.dao.admin.IBlogDao;
import com.wang.blog.dao.admin.ITagDao;
import com.wang.blog.dao.admin.ITypeDao;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.IBlogService;
import com.wang.blog.tree.CommentTree;
import com.wang.blog.util.MarkdownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Retention;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author wangsiyuan
 */
@Service
public class BlogServiceImpl implements IBlogService {

    private IBlogDao blogDao;

    private ITagDao tagDao;

    private ITypeDao typeDao;

    private ICommentDao commentDao;

    private IBlogByRedis blogByRedis;

    private ITagBlogByRedis tagBlogByRedis;

    private ITagByRedis tagByRedis;

    private ITypeByRedis typeByRedis;

    private ICommentByRedis commentByRedis;

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setTypeDao(ITypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Autowired
    public void setCommentByRedis(ICommentByRedis commentByRedis) {
        this.commentByRedis = commentByRedis;
    }

    @Autowired
    public void setTagByRedis(ITagByRedis tagByRedis) {
        this.tagByRedis = tagByRedis;
    }

    @Autowired
    public void setTypeByRedis(ITypeByRedis typeByRedis) {
        this.typeByRedis = typeByRedis;
    }

    @Autowired
    public void setTagBlogByRedis(ITagBlogByRedis tagBlogByRedis) {
        this.tagBlogByRedis = tagBlogByRedis;
    }

    @Autowired
    public void setBlogByRedis(IBlogByRedis blogByRedis) {
        this.blogByRedis = blogByRedis;
    }

    @Autowired
    public void setBlogDao(IBlogDao blogDao) {
        this.blogDao = blogDao;
    }
    @Autowired
    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }
    @Autowired
    public void setCommentDao(ICommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * 获取所有的标签Id
     * @param tags 该博客的所有标签
     * @return 返回String类型的标签id,"1","2","3"
     */
    @Override
    public String getTagIds(List<Tag> tags) {
        StringBuilder stringBuffer = new StringBuilder();
//        将标签的Id转换为StringBuffer类型的,("1","2","3","4")
        for (int i = 0 ;i < tags.size();i++){
            stringBuffer.append(tags.get(i).getTag_id());
            if(i != tags.size() - 1){
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

//    完成
    @Override
    public Blog getBlog(Long id) {
        Blog blog = blogByRedis.getBlogById(id);
        if(blog == null){
            blog = getMarkDownBlog(id);
        }
        return blog;
    }

//    完成
    @Override
    public Blog getMarkDownBlog(Long id) {
        Blog blog = blogByRedis.getBlogById(id);
        if(blog.getId() == null){
            blog = blogDao.getBlogById(id);
            if(blog == null){
                throw new NotFindException("该博客不存在");
            }
//          将博客的内容MarkDown格式转化为HTML
            blog.setContent(MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));
            blog.setType(typeByRedis.getTypeById(blog.getType_id()));
            List<Integer> tagIds = tagBlogByRedis.listTagByBlogId(blog.getId());
            if(tagIds.size() == 0){
                List<Tag> tags = tagDao.getTagByBlogId(blog.getId());
            }
            tagIds = tagBlogByRedis.listTagByBlogId(blog.getId());
            List<Tag> tags = new ArrayList<>();
            for (Integer tagId : tagIds){
                Tag tag = tagByRedis.getTagById((long) tagId);
                tags.add(tag);
            }
            blog.setTagList(tags);
            blogByRedis.saveBlog(blog);

        }
        return blog;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateView(Long id) {
        blogDao.updateView(id);
        Blog blog = blogByRedis.getBlogById(id);
        blog.setViews(blog.getViews() + 1);
        blogByRedis.saveBlog(blog);
//        blogByRedis.delBlog(id);
//        checkSize();
    }

    @Override
    public Page<Blog> searchBlogByString(Page<Blog> page, String query) {
        if(query != null){
//            为查询字段添加百分号
            query = tranQuery(query);
        }
        page.setPage_count(blogDao.countSearch(query));
        if(page.getPage_count() != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogDao.searchBlog(start, page.getPage_size(), query));
        }
        return page;
    }

    @Override
    public Page<Blog> listBlog(Page<Blog> page, Blog blog) {
        if(blog.getTitle() != null){
//            为查询字段添加百分号
            blog.setTitle(tranQuery(blog.getTitle()));
        }
        int count = blogByRedis.countBlogByType(blog.getType_id());
        page.setPage_count(count);
        if(count != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogByRedis.listBlogOnPage(start,page.getPage_size(),blogByRedis.listBlogByType(blog.getType_id())));
        }
        return page;
    }

    /**
     *  为搜索的字段添加%传入SQL进行查询
     * @param query 要作为搜索的字段
     * @return 处理完的字段
     */
    private String tranQuery(String query){
        return "%" + query + "%";
    }

    @Override
    public Page<Blog> listBlogByTag(Page<Blog> page, Long tagId) {
        checkSize();
        int count = blogByRedis.countBlogByTag(tagId);
        page.setPage_count(count);
        if(count != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogByRedis.listBlogOnPage(start,page.getPage_size(),blogByRedis.listBlogByTag(tagId)));
//            page.setList(blogDao.listTagIncludeBlog(start,page.getPage_size(), tagId));
        }
        return page;
    }

    @Override
    public Page<Blog> listBlog(Page<Blog> page) {
        page.setPage_count(blogByRedis.countByBlog());
        Page<Blog> curBlog = getPageTot(page);
        int start  = getStart(curBlog);
        checkSize();
        page.setList(blogByRedis.listBlogWithUpTimeOnPage(start,page.getPage_size()));
        return page;
    }

//    完成
    @Override
    public List<Blog> listBlogByTime() {
        checkSize();
        return blogByRedis.listBlogWithUpTime();
    }


//    完成
    @Override
    public int getBlogCount() {
        return blogByRedis.countByBlog();
    }

    /**
     * 计算当前分页情况下需要从数据库从获取第几条到第几条的数据
     * @param page 分页的情况
     * @return 返回计算完成后的分页情况
     */
    private int getStart(Page<Blog> page){
        return page.getPage_size() * (page.getCur_Page() - 1);
    }

    /**
     * 计算当前一页存放N条情况下,总共有多少页
     * @param page 分页的情况
     * @return 返回计算完成后的分页情况
     */
    private Page<Blog> getPageTot(Page<Blog> page){
        page.setPage_tot((page.getPage_count() / page.getPage_size()) + (page.getPage_count() % page.getPage_size() == 0 ? 0 :1));
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
        return page;
    }

    @Override
    public List<Blog> getBlogOnPage(Page<Blog> page) {
        int count = blogByRedis.countByBlog();
        page.setPage_count(count);
        getPageTot(page);
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
        int start = getStart(page);
        checkSize();
        return blogByRedis.listBlogOnPage(start,page.getPage_size());
    }



//    完成（可能需要重新处理事务）
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Blog saveBlog(Blog blog,List<Tag> tags) {
//        设置创建时间
        blog.setCreateTime(new Date());
//        设置更新时间
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogDao.saveBlog(blog);
//        再次获取博客Id,对标签进行存储
        Long bid = blog.getId();
        for (Tag tag : tags){
            blogDao.saveBlogTag(bid,tag.getTag_id());
        }
        syncRedisByBlog(blog);
        blogByRedis.addSize();
        return blog;
    }

    /**
     * 将Date类型的时间转化为String
     * @param time Date类型的时间
     *
     */
    private Date formatToString(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return formatToDate(dateFormat.format(time));
    }

    /**
     * 将String类型的时间转化为Date
     * @param time String类型的时间
     *
     */
    private Date formatToDate(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

//    完成
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlog(Long id, Blog blog) {
//        重新设置更新时间
        blog.setUpdateTime(new Date());
        blog.setViews(blogDao.getView(blog.getId()));
        blog.setCreateTime(blogDao.getCreatTime(blog.getId()));
        blogDao.updateBlog(id,blog);
        tagDao.deleteTagBlog(blog.getId());
        for (Tag tag : blog.getTagList()){
            blogDao.saveBlogTag(blog.getId(),tag.getTag_id());
        }
        blogByRedis.delBlog(blog.getId());
    }

//  完成
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlog(Long id) {
        //blog作为外键，先删除评论
        deleteComment(id);
        tagDao.deleteTagBlog(id);
        blogDao.deleteBlog(id);
        blogByRedis.delBlog(id);
        blogByRedis.decSize();
    }

//  完成
    /**
     * 删除所有评论功能主体部分,构造多叉树,进行深搜对评论进行删除
     * @param id 博客Id
     * @see CommentTree 多叉树的节点
     */
    @Override
    public void deleteComment(Long id){
//      将要删除博客的评论查询出来
        List<Comment> commentList = commentDao.listCommentByBlogId(id);
//      构造一颗评论多叉树的根节点，根节点为空
        CommentTree rootTree = new CommentTree();
        for (Comment comment : commentList){
//          如果父Id为空的话,一定是评论根节点
            if(comment.getParentId() == null){
                CommentTree childComment = new CommentTree();
                childComment.setComment(comment);
//              放在空的根节点下,将这个评论根节点的一系列子节点先加入多叉树中,再去寻找下一个评论根节点
                addCommentTree(commentList,childComment);
                rootTree.getCommentList().add(childComment);
            }
        }
//        将构造好的多叉树传入,正式开始删除
        deleteComment(rootTree);
    }


    //生成评论多叉树

    /**
     * 进行搜索,将第一个评论根节点及其子节点加入树中
     * @param commentList 所有的评论
     * @param parentComment 评论的根节点,递归调用所以这个根节点是相对的
     */
    private void addCommentTree(List<Comment> commentList,CommentTree parentComment){
        for (Comment comment : commentList){
            if(comment.getParentId() != null){
//                查找parentComment的Id和子节点的父Id是否匹配,匹配则为子节点
                if(comment.getParentId().equals(parentComment.getComment().getId())){
                    CommentTree childComment = new CommentTree();
                    childComment.setComment(comment);
//                    递归寻找下一个子节点
                    addCommentTree(commentList,childComment);
//                    回溯,将子节点一级一级向上添加
                    parentComment.getCommentList().add(childComment);
                }
            }
        }
    }

    /**
     * 后序遍历评论多叉树，并删除
     * @param rootTree 评论多叉树
     */
    private void deleteComment(CommentTree rootTree){
        for (CommentTree childComment : rootTree.getCommentList()){
//            寻找叶子节点
            if(childComment.getCommentList().size() > 0){
//                不是叶子节点,再次传入子节点,进行递归
                deleteComment(childComment);
            }
//            寻找到叶子节点,进行删除
            commentDao.deleteCommentById(childComment.getComment().getId());
        }
    }



//    完成
    /**
     *  归档功能,找出各个年份对应的博客
     */
    @Override
    public Map<String, List<Blog>> listBlogByYear() {
//        获取已有的博客年份
        List<String> year = blogByRedis.getYear();
//        一个年份对应一个List<Blog>
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        for (String str : year){
//            传入年份进行查找博客
            map.put(str,blogByRedis.getBlogByYear(str));
        }
        return map;
    }

    private void checkSize() {
        @SuppressWarnings("all")
        int daoCount = (int)redisTemplate.opsForValue().get("BlogSize");
        int redisCount = blogByRedis.countByBlog();
        if(redisCount != daoCount){
            List<Blog> blogs = blogDao.listBlogAll();
            for (Blog blog : blogs){
                syncRedisByBlog(blog);
            }
        }
    }

    private void syncRedisByBlog(Blog blog){
        //          将博客的内容MarkDown格式转化为HTML
        blog.setContent(MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));
        blogByRedis.saveBlog(blog);
    }
}



