package com.qf.moviesys.service;


import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.pojo.Ticket;

import java.util.List;

public interface CustomerService {
    List<Movie> findMovieByStatus();

    List<Ticket> findTicketBySid(Integer scheduleId);

    boolean updateTicket(int ticketId, Integer userId) ;

    List<Schedule>  findScheduleTicket(Integer movieId);

    void updateTicketBySync(Integer tid,Integer userId);
}
