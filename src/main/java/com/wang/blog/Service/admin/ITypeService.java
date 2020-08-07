package com.wang.blog.Service.admin;

import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Type;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;

public interface ITypeService {

    public void saveType(String name);

    public Type getType(Long id);

    public List<Type> lisTpeByCount();

    public Page<Type> listType(Page<Type> page);

    public int getTypeCount();

    public void updateType(Long id,String name);

    public void deleteType(Long id);

    public Type getTypeByName(String name);

    public List<Type> listType();
}
