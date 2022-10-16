package com.qf.moviesys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Ticket;

import java.util.List;

public interface TicketMapper extends BaseMapper<Ticket> {


    void updateSchedule(Integer id);

    void insertTicket();

    List<Movie> findMovieTicketByTitle(String title);
}
