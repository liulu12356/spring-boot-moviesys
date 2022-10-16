package com.qf.moviesys.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.moviesys.dao.CategoryMapper;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Override
    public Category findById(Integer id) {

        return categoryMapper.findById(id);
    }

    @Override
    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
        categoryMapper.deleteMovieByCategory(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateCategory(category);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryMapper.findByName(name);
    }


    @Override
    public IPage<Category> findByPage(int current, int size) {
        Page<Category> page=new Page<>(current,size);
        QueryWrapper<Category> wrapper =new QueryWrapper<>();
        wrapper.eq("state",1);
        return  super.page(page,wrapper);
    }

    @Override
    public IPage<Category> findByKeywordPage(int current, int size, String keyword) {
        Page<Category> page=new Page<>(current,size);
        return  categoryMapper.findByKeywordPage(page,keyword);
    }
}
