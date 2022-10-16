package com.qf.moviesys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleMapper extends BaseMapper<Schedule> {
    List<Movie> findScheduleMovie();

    List<Schedule> findScheduleListByMovieId(Integer id);

    void save(Schedule schedule);

    void delete(Integer id);

    void updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    Schedule findScheduleById(Integer scheduleId);

    IPage<Movie> findMoviesNoGearPage(Page<Category> page);
}
