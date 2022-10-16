package com.qf.moviesys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.moviesys.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    IPage<Category> findByKeywordPage(Page<Category> page, @Param("keyword") String keyword);


    List<Category> findAll();

    Category findById(Integer id);

    void deleteCategory(Integer id);

    void insertCategory(Category category);

    void updateCategory(Category category);

    void deleteMovieByCategory(Integer category);


    List<Category> findByName(String name);


}
