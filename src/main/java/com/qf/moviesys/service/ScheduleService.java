package com.qf.moviesys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;

import java.util.List;

public interface ScheduleService {

    //查询未排挡的电影列表
    List<Movie> findMoviesNoGear();


    List<Schedule> findScheduleByMovie(Integer id);

    void deleteSchedule(Integer id);

    void insertSchedule(Schedule schedule);

    Schedule findScheduleById(Integer scheduleId);

    IPage<Movie> findMoviesNoGearPage(Integer current, Integer size);
}
