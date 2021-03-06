package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.bean.Type;
import com.wang.blog.cache.redis.ITypeByRedis;
import com.wang.blog.dao.admin.ITypeDao;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.ITypeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangsiyuan
 */
@Service
public class TypeServiceImpl implements ITypeService {


    private ITypeDao typeDao;

    private ITypeByRedis typeByRedis;

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setTypeByRedis(ITypeByRedis typeByRedis) {
        this.typeByRedis = typeByRedis;
    }

    @Autowired
    public void setTypeDao(ITypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveType(String name) {
        typeDao.saveType(name);
        typeByRedis.addSize();
    }

    @Override
    public Type getType(Long id) {
        Type type;
        type = typeByRedis.getTypeById(id);
        if(type == null){
            type = typeDao.getTypeById(id);
            if(type != null){
                typeByRedis.setType(type);
            }
        }
        return type;
    }

    @Override
    public List<Type> lisTypeByCount() {
        checkTypeSize();
        return typeByRedis.countTypeByBlog(0,6);
    }

    @Override
    public Page<Type> listType(@NotNull Page<Type> page) {
        checkTypeSize();
        page.setPage_count(Math.toIntExact(typeByRedis.countType()));
//        计算当前一页存放N条情况下,总共有多少页
        page.setPage_tot(page.getPage_count() / page.getPage_size() + page.getPage_count() / page.getPage_size() == 0 ? 0 :1);
//        如果分页不足一页
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
//        计算当前分页情况下需要从数据库从获取第几条到第几条的数据
        int start = page.getPage_size() * (page.getCur_Page() - 1);
        checkTypeSize();
        page.setList(typeByRedis.getTypeByPage(start, page.getPage_size()));
        return page;
    }

    @Override
    public Long countType() {
        checkTypeSize();
        return typeByRedis.countType();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateType(Long id,String name) {
//        通过Id获取标签
        Type type = typeDao.getTypeById(id);
        if(type == null){
            throw new NotFindException();
        }
        typeDao.updateType(id, name);
        type.setName(name);
        typeByRedis.updateType(id,type);
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteType(id);
        typeByRedis.deleteType(id);
        typeByRedis.decSize();
    }

    @Override
    public Type getTypeByName(String name) {
        Type type = typeByRedis.getTypeByName(name);
        if(type == null){
            type = typeDao.getTypeByName(name);
            if(type != null){
                typeByRedis.setType(type);
            }
        }
        return type;
    }

    @Override
    public List<Type> listType() {
        checkTypeSize();
        return typeByRedis.listType();
    }

    private void checkTypeSize(){
        Long redisCount = typeByRedis.countType();
        @SuppressWarnings("all")
        int daoCount = (int)redisTemplate.opsForValue().get("TypeSize");
        if(redisCount != daoCount){
//            获取完整的Type,包括分类所拥有的博客的数

            List<Type> types = typeDao.listTypeAll();
            for(Type type : types){
                typeByRedis.setType(type);  
            }
        }
    }

}
