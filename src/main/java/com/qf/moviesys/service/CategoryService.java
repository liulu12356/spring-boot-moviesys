package com.qf.moviesys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> findAll();

    Category findById(Integer id);

    void insertCategory(Category category);

    void deleteCategory(Integer id);

    void updateCategory(Category category);

    List<Category> findByName(String name);

    IPage<Category> findByPage(int current, int size);

    IPage<Category> findByKeywordPage(int current, int size, String keyword);
}