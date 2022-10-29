package com.qf.moviesys.web;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.service.MoviesService;
import com.qf.moviesys.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TicketController {
    final MoviesService moviesService;
    @Autowired
    TicketService ticketService;


    public TicketController(MoviesService moviesService) {
        this.moviesService = moviesService;

    }

    @GetMapping("/ticket/findByTitle/{keyword}")
    List<Movie> findMovieByTitle(@PathVariable("keyword") String title){
        return ticketService.findMovieByTitle(title);

    }

    @GetMapping("/ticket/page")
    IPage<Movie> findMovieTicketPage(Integer current, Integer size) {
        return moviesService.findMovieTicket(current,size);
    }

    @GetMapping("/ticket")
    List<Movie> findMovieHaveTicket(){
        return moviesService.findMovieHaveTicket();
    }

    @PutMapping("/ticket")
    String updateTicket(@RequestBody Schedule schedule) {
        ticketService.SaveTicket(schedule);
        return "UpdateOK";
    }
}
