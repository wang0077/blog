package com.wang.blog.Dao.admin;

import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@Mapper
public interface ITypeDao {

    public Type getTypeByName(@Param("name") String name);

    public void saveType(@Param("name") String name);

    public Type getTypeById(@Param("id") Long id);

    public List<Type> listType(@Param("start") int start, @Param("end") int end);

    public List<Type> listTypeByCount(@Param("start") int start,@Param("end") int end);

    public void updateType(@Param("id") Long id,@Param("name") String name);

    public void deleteType(@Param("id") Long id);

    public int getTypeCount();

    public List<Type> getTypeAll();
}
