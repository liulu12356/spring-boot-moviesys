package com.qf.moviesys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.pojo.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MoviesService {

    List<Movie> findAll();

    Movie findById(Integer id);

    void insertMovie(Movie movie, List<Integer> categoryIdList);

    void deleteMovie(Integer id);

    void updateMovie(Movie movie, List<Integer> categoryList);

    List<Movie> findByTitle(String title);

    List<Movie> findByCategory(List<Integer> categoryId);

    List<Movie> findByCategoryNoGear(List<Integer> categoryIdList);

    List<Movie> findByTitleNoGear(String s);

    List<Category> findCategoryListByMovie(Integer id);

    void updateStatus(Integer id);

    IPage<Movie> findMovieTicket(Integer current, Integer size);

    IPage<Movie> findByPage(Integer current, Integer size);

    IPage<Movie> findByKeywordPage(Integer current, Integer size, String keyword);

    IPage<Movie> findByCategoryPage(Integer current, Integer size, List<Integer> categoryIdList);

    IPage<Movie> findByTitlePageNoGear(Integer current, Integer size, String keyword);

    List<Movie> findMovieHaveTicket();
}
