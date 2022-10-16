package com.qf.moviesys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.pojo.Movie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MoviesMapper extends BaseMapper<Movie> {


    List<Movie> findAll();

    Movie findById(Integer id);

    void deleteMovie(Integer id);


    void insertMovie(Movie movie);


    void deleteCategoryByMovies(Integer movieId);

    void updateMovie(Movie movie);

    List<Movie> findByTitle(String title);

    List<Movie> findByCategory(List<Integer> categoryId);

    List<Movie> findByCategoryNoGear(List<Integer> categoryIdList);

    List<Movie> findByTitleNoGear(String s);

    List<Category> findCategoryListByMovie(Integer id);

    Integer findMovieIdBy(String title);

    void insertMovieCategory(@Param("movieId") Integer movieId, @Param("categoryId") Integer categoryId);

    void updateMovieStatus(Integer id);

    void updateStatus(Integer id);

    IPage<Movie> findMovieTicket(Page<Movie> page);

    List<Movie> findMovieTicket();

    IPage<Movie> findByKeywordPage(Page<Movie> page, @Param("keyword") String keyword);

    IPage<Movie> findByCategoryPage(Page<Movie> page, @Param("list") List<Integer> categoryIdList);

    IPage<Movie> findByTitlePageNoGear(Page<Movie> page, @Param("keyword") String keyword);
}
