package com.wang.blog.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangsiyuan
 */
public class Page<T> implements Serializable {
    /**
     * 当前页码数
     */
    private int cur_Page;

    /**
     * 每页条数
     */
    private int Page_size;
    /**
     * 总页数
     */
    private int Page_tot;
    /**
     * 总条目数
     */
    private int Page_count;
    /**
     * 搜索时的内容
     */
    private String name;
    /**
     * Page放置的内容,设置为泛型,可以放置博客,分类,标签等实体
     */
    private List<T> list;

    public Page() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCur_Page() {
        return cur_Page;
    }

    public void setCur_Page(int cur_Page) {
        this.cur_Page = cur_Page;
    }

    public int getPage_size() {
        return Page_size;
    }

    public void setPage_size(int page_size) {
        Page_size = page_size;
    }

    public int getPage_tot() {
        return Page_tot;
    }

    public void setPage_tot(int page_tot) {
        Page_tot = page_tot;
    }

    public int getPage_count() {
        return Page_count;
    }

    public void setPage_count(int page_count) {
        Page_count = page_count;
    }

    @Override
    public String toString() {
        return "Page{" +
                "cur_Page=" + cur_Page +
                ", Page_size=" + Page_size +
                ", Page_tot=" + Page_tot +
                ", Page_count=" + Page_count +
                '}';
    }
}
