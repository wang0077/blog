package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Comment;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.dao.ICommentDao;
import com.wang.blog.dao.admin.IBlogDao;
import com.wang.blog.dao.admin.ITagDao;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.IBlogService;
import com.wang.blog.tree.CommentTree;
import com.wang.blog.util.MarkdownUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BlogServiceImpl implements IBlogService {

    private IBlogDao blogDao;

    private ITagDao tagDao;

    private ICommentDao commentDao;

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
        for (int i = 0 ;i < tags.size();i++){
            stringBuffer.append(tags.get(i).getTag_id());
            if(i != tags.size() - 1){
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public Blog getMarkDownBlog(Long id) {
        Blog blog = blogDao.getBlogById(id);
        if(blog == null){
            throw new NotFindException("该博客不存在");
        }
//        UtilFactory factory = new MarkDownFactory();
//        Markdown markdown = (Markdown) factory.make();
        blog.setContent(MarkdownUtil.markdownToHtmlExtensions(blog.getContent()));
        return blog;
    }

    @Override
    @Transactional
    public void updateView(Long id) {
        blogDao.updateView(id);
    }

    @Override
    public Page<Blog> searchBlogByString(Page<Blog> page, String query) {
        if(query != null){
            query = "%" + query + "%";
        }
        page.setPage_count(blogDao.countSearch(query));
        List<Blog> blogs = null;
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
            blog.setTitle("%" + blog.getTitle() + "%");
        }
        int count = blogDao.countTypeIncludeBlog(blog);
        page.setPage_count(count);
        if(count != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogDao.listBlog(start,page.getPage_size(),blog));
        }
        return page;
    }

    @Override
    public Page<Blog> listBlogByTag(Page<Blog> page, Long tagId) {
        int count = blogDao.countBlogByTag(tagId);
        page.setPage_count(count);
        if(count != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogDao.listTypeIncludeBlog(start,page.getPage_size(), tagId));
        }
        return page;
    }

    @Override
    public Page<Blog> listBlog(Page<Blog> page) {
        page.setPage_count(blogDao.countBlog());
        Page<Blog> curBlog = getPageTot(page);
        int start  = getStart(curBlog);
        page.setList(blogDao.listBlogByTime(start,page.getPage_size()));
        return page;
    }

    @Override
    public List<Blog> listBlogByTime() {
        return blogDao.listBlogByUpTime();
    }

//    @Override
//    public List<Blog> listBlogByFoot() {
//        return blogDao.listBlogByUpTimeFoot();
//    }

    @Override
    public int getBlogCount() {
        return blogDao.countBlog();
    }

    private int getStart(Page<Blog> page){
        return page.getPage_size() * (page.getCur_Page() - 1);
    }

    private Page<Blog> getPageTot(Page<Blog> page){
        page.setPage_tot((page.getPage_count() / page.getPage_size()) + (page.getPage_count() % page.getPage_size() == 0 ? 0 :1));
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
        return page;
    }

    @Override
    public List<Blog> getBlogOnPage(Page<Blog> page) {
        int count = blogDao.countBlog();
        page.setPage_count(count);
        int curItem = (page.getCur_Page() - 1) * page.getPage_size();
        page.setPage_tot((page.getPage_count() / page.getPage_size()) + (page.getPage_count() % page.getPage_size() == 0 ? 0 :1));
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
        int start = page.getPage_size() * (page.getCur_Page() - 1);
        return blogDao.listBlogWithType(start,page.getPage_size());
    }



    @Override
    @Transactional
    public Blog saveBlog(Blog blog,List<Tag> tags) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogDao.saveBlog(blog);
        blog = blogDao.getBlogByTitle(blog.getTitle());
        Long bid = blog.getId();
        for (Tag tag : tags){
            blogDao.saveBlogTag(bid,tag.getTag_id());
        }
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
            Date parse = dateFormat.parse(time);
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlog(Long id, Blog blog) {
        blog.setUpdateTime(new Date());
        blog.setViews(blogDao.getView(blog.getId()));
        blog.setCreateTime(blogDao.getCreatTime(blog.getId()));
        blogDao.updateBlog(id,blog);
        tagDao.deleteTagBlog(blog.getId());
        for (Tag tag : blog.getTagList()){
            blogDao.saveBlogTag(blog.getId(),tag.getTag_id());
        }
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        //blog作为外键，先删除评论
        deleteComment(id);
        tagDao.deleteTagBlog(id);
        blogDao.deleteBlog(id);
    }


    //删除评论功能主体部分
    @Override
    public void deleteComment(Long id){
        //将要删除博客的评论查询出来
        List<Comment> commentList = commentDao.listCommentByBlogId(id);
        //构造一颗评论多叉树的根节点，根节点为空
        CommentTree RootTree = new CommentTree();
        for (Comment comment : commentList){
            if(comment.getParentId() == null){
                CommentTree childComment = new CommentTree();
                childComment.setComment(comment);
                addCommentTree(commentList,childComment);
                RootTree.getCommentList().add(childComment);
            }
        }
        DeleteComment(RootTree);
    }


    //生成评论多叉树
    public void addCommentTree(List<Comment> commentList,CommentTree parentComment){
        for (Comment comment : commentList){
            if(comment.getParentId() != null){
                if(comment.getParentId().equals(parentComment.getComment().getId())){
                    CommentTree childComment = new CommentTree();
                    childComment.setComment(comment);
                    addCommentTree(commentList,childComment);
                    parentComment.getCommentList().add(childComment);
                }
            }
        }
    }


    //后序遍历评论多叉树，并删除
    public void  DeleteComment(CommentTree RootTree){
        for (CommentTree childComment : RootTree.getCommentList()){
            if(childComment.getCommentList().size() > 0){
                DeleteComment(childComment);
            }
            commentDao.deleteCommentById(childComment.getComment().getId());
        }
    }


    @Override
    public Map<String, List<Blog>> listBlogByYear() {
        List<String> year = blogDao.getYear();
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        for (String str : year){
            map.put(str,blogDao.getBlogByYear(str));
        }
        return map;
    }
}
