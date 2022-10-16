package com.qf.moviesys.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CategoryController {
    final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/category/page")
    IPage<Category> findByPage(int current, int size) {
        return categoryService.findByPage(current,size);
    }


    @GetMapping("/category/{id}")
    Category findById(@PathVariable("id") Integer id) {
        return categoryService.findById(id);
    }

    @GetMapping("/category/findByName/{name}")
    List<Category> findByName(@PathVariable("name") String name) {
        return categoryService.findByName("%"+name+"%");
    }


    @GetMapping("/category/findByKeyword/page/{keyword}")
    IPage<Category> findByKeywordPage(int current, int size, @PathVariable("keyword") String keyword) {
        return categoryService.findByKeywordPage(current,size,"%"+keyword+"%");
    }


    @PostMapping("/category")
    String insertCategory(@RequestBody Category category) {
        categoryService.insertCategory(category);
        return "SaveOK";
    }

    @PutMapping("/category")
    String updateCategory(@RequestBody Category category) {

        categoryService.updateCategory(category);
        return "UpdateOK";
    }

    @DeleteMapping("/category/{id}")
    String deleteCategory(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        return "OK";
    }


}
