package com.qf.moviesys.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.moviesys.dao.MoviesMapper;
import com.qf.moviesys.dao.ScheduleMapper;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.service.MoviesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesServiceImpl extends ServiceImpl<MoviesMapper, Movie> implements MoviesService {

    final MoviesMapper moviesMapper;
    final ScheduleMapper scheduleMapper;

    public MoviesServiceImpl(MoviesMapper moviesMapper, ScheduleMapper scheduleMapper) {
        this.moviesMapper = moviesMapper;
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public List<Movie> findAll() {
        return moviesMapper.findAll();
    }

    @Override
    public Movie findById(Integer id) {
        return moviesMapper.findById(id);
    }

    @Override
    public void insertMovie(Movie movie, List<Integer> categoryIdList) {
        moviesMapper.insertMovie(movie);
        final Integer movieId = moviesMapper.findMovieIdBy(movie.getTitle());
        categoryIdList.forEach((categoryId)-> moviesMapper.insertMovieCategory(movieId,categoryId));
    }

    @Override
    public void deleteMovie(Integer id) {
        moviesMapper.deleteMovie(id);
        moviesMapper.deleteCategoryByMovies(id);
    }

    @Override
    public void updateMovie(Movie movie, List<Integer> categoryIdList) {
        moviesMapper.updateMovie(movie);
        moviesMapper.deleteCategoryByMovies(movie.getId());
        categoryIdList.forEach((categoryId)-> moviesMapper.insertMovieCategory(movie.getId(),categoryId));
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return moviesMapper.findByTitle(title);
    }

    @Override
    public List<Movie> findByCategory(List<Integer> categoryId) {
        return moviesMapper.findByCategory(categoryId);
    }

    @Override
    public List<Movie> findByCategoryNoGear(List<Integer> categoryIdList) {
        return moviesMapper.findByCategoryNoGear(categoryIdList);
    }

    @Override
    public List<Movie> findByTitleNoGear(String s) {
        return moviesMapper.findByTitleNoGear(s);
    }

    @Override
    public List<Category> findCategoryListByMovie(Integer id) {
        return moviesMapper.findCategoryListByMovie(id);
    }

    @Override
    public void updateStatus(Integer id) {
        moviesMapper.updateStatus(id);
    }

    @Override
    public IPage<Movie> findMovieTicket(Integer current, Integer size) {
        Page<Movie> page=new Page<>(current,size);
        IPage<Movie> movieIPage=moviesMapper.findMovieTicket(page);
        final List<Movie> movieTicket =movieIPage.getRecords();
        movieTicket.forEach((movie)->{
            final List<Schedule> scheduleList = scheduleMapper.findScheduleListByMovieId(movie.getId());
            movie.setScheduleList(scheduleList);
        });
        movieIPage.setRecords(movieTicket);
        return movieIPage;
    }

    @Override
    public IPage<Movie> findByPage(Integer current, Integer size) {
        Page<Movie> page=new Page<>(current,size);
        QueryWrapper<Movie> wrapper =new QueryWrapper<>();
        wrapper.eq("state",1);
        return super.page(page,wrapper);
    }

    @Override
    public IPage<Movie> findByKeywordPage(Integer current, Integer size, String keyword) {
        Page<Movie> page= new Page<>(current,size);
        return moviesMapper.findByKeywordPage(page,keyword);
    }

    @Override
    public IPage<Movie> findByCategoryPage(Integer current, Integer size, List<Integer> categoryIdList) {
        Page<Movie> page= new Page<>(current,size);
        return  moviesMapper.findByCategoryPage(page,categoryIdList);

    }

    @Override
    public IPage<Movie> findByTitlePageNoGear(Integer current, Integer size, String keyword) {
        Page<Movie> page= new Page<>(current,size);
        return moviesMapper.findByTitlePageNoGear(page,keyword);
    }

    @Override
    public List<Movie> findMovieHaveTicket() {
        List<Movie> movieTicket=moviesMapper.findMovieTicket();
        movieTicket.forEach((movie)->{
            final List<Schedule> scheduleList = scheduleMapper.findScheduleListByMovieId(movie.getId());
            movie.setScheduleList(scheduleList);
        });
        return movieTicket;
    }


}
