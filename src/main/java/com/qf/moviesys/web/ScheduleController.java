package com.qf.moviesys.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;


    @GetMapping("/movie/noGear")
    List<Movie> findMoviesNoGear(){
        return scheduleService.findMoviesNoGear();
    }

    @GetMapping("/movie/noGear/page")
    IPage<Movie> findMoviesNoGearPage(Integer current, Integer size){
        return scheduleService.findMoviesNoGearPage(current,size);
    }

    @GetMapping("/schedule/{movieId}")
    List<Schedule> findScheduleByMovie(@PathVariable("movieId") Integer id){
        return scheduleService.findScheduleByMovie(id);
    }

    @GetMapping("/schedule/ticket/{scheduleId}")
    Schedule findScheduleById(@PathVariable Integer scheduleId){
        return scheduleService.findScheduleById(scheduleId);
    }

    @DeleteMapping("/schedule/{id}")
    String deleteSchedule(@PathVariable("id") Integer id){
        scheduleService.deleteSchedule(id);
        return "OK";
    }

    @PostMapping("/schedule")
    String insertSchedule(@RequestBody Schedule schedule){
        scheduleService.insertSchedule(schedule);
        return "SaveOK";
    }



}
