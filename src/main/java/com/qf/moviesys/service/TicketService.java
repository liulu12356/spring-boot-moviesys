package com.qf.moviesys.service;


import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;

import java.util.List;

public interface TicketService {

    void SaveTicket(Schedule schedule);

    List<Movie> findMovieByTitle(String title);
}
