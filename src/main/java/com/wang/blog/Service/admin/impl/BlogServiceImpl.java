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

    @Autowired
    private IBlogDao blogDao;

    @Autowired
    private ITagDao tagDao;

    @Autowired
    private ICommentDao commentDao;

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
    public void UpdateView(Long id) {
        blogDao.updateView(id);
    }

    @Override
    public Page<Blog> searchBlog(Page<Blog> page, String query) {
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
        System.out.println("count : " + count );
        System.out.println(count);
        if(count != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogDao.listBlog(start,page.getPage_size(),blog));
        }
        System.out.println(count);
        return page;
    }

    @Override
    public Page<Blog> listBlogByTag(Page<Blog> page, Long TagId) {
        int count = blogDao.countBlogByTag(TagId);
        page.setPage_count(count);
        System.out.println(count);
        if(count != 0){
            getPageTot(page);
            int start = getStart(page);
            page.setList(blogDao.listTypeIncludeBlog(start,page.getPage_size(),TagId));
        }
        return page;
    }

    @Override
    public Page<Blog> listBlog(Page<Blog> page) {
        page.setPage_count(blogDao.countBlog());
        Page<Blog> Cur_blog = getPageTot(page);
        int start  = getStart(Cur_blog);
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
    public List<Blog> getBlogAll(Page<Blog> page) {
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

    @Override
    public Date format_toString(Date time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return format_toDate(dateFormat.format(time));
    }

    @Override
    public Date format_toDate(String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dateFormat.parse(time);
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional
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
        DeleteComment(id);
        tagDao.deleteTagBlog(id);
        blogDao.deleteBlog(id);
    }


    //删除评论功能主体部分
    @Override
    public void DeleteComment(Long id){
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
