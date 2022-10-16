package com.qf.moviesys.serviceImpl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.moviesys.dao.MoviesMapper;
import com.qf.moviesys.dao.ScheduleMapper;
import com.qf.moviesys.pojo.Category;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    final ScheduleMapper scheduleMapper;
    final MoviesMapper moviesMapper;



    public ScheduleServiceImpl(ScheduleMapper scheduleMapper, MoviesMapper moviesMapper) {
        this.scheduleMapper = scheduleMapper;
        this.moviesMapper = moviesMapper;
    }

    @Override
    public List<Movie> findMoviesNoGear() {
        return scheduleMapper.findScheduleMovie();
    }

    @Override
    public List<Schedule> findScheduleByMovie(Integer id) {
        return scheduleMapper.findScheduleListByMovieId(id);
    }

    @Override
    public void deleteSchedule(Integer id) {
        scheduleMapper.delete(id);
    }

    @Override
    public void insertSchedule(Schedule schedule) {
        scheduleMapper.save(schedule);
        moviesMapper.updateMovieStatus(schedule.getMovieId());
    }

    @Override
    public Schedule findScheduleById(Integer scheduleId) {
        return scheduleMapper.findScheduleById(scheduleId);
    }

    @Override
    public IPage<Movie> findMoviesNoGearPage(Integer current, Integer size) {
        Page<Category> page= new Page<>(current,size);
        return scheduleMapper.findMoviesNoGearPage(page);
    }
}
