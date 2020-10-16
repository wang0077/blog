package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Type;
import com.wang.blog.dao.admin.ITypeDao;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.ITypeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangsiyuan
 */
@Service
public class TypeServiceImpl implements ITypeService {


    private ITypeDao typeDao;
    @Autowired
    public void setTypeDao(ITypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveType(String name) {
        typeDao.saveType(name);
    }

    @Override
    public Type getType(Long id) {
        return typeDao.getTypeById(id);
    }

    @Override
    public List<Type> lisTypeByCount() {
        return typeDao.listTypeByCountBlog(0,6);
    }

    @Override
    public Page<Type> listType(@NotNull Page<Type> page) {
        page.setPage_count(typeDao.countType());
        page.setPage_tot(page.getPage_count() / page.getPage_size() + page.getPage_count() / page.getPage_size() == 0 ? 0 :1);
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
        int start = page.getPage_size() * (page.getCur_Page() - 1);
        page.setList(typeDao.listType(start,page.getPage_size()));
        return page;
    }

    @Override
    public int countType() {
        return typeDao.countType();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateType(Long id,String name) {
        Type type = typeDao.getTypeById(id);
        if(type == null){
            throw new NotFindException();
        }
        typeDao.updateType(id, name);
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.getTypeByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeDao.listTypeAll();
    }
}
