package com.qf.moviesys.serviceImpl;


import com.qf.moviesys.dao.CustomerMapper;
import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.pojo.Ticket;
import com.qf.moviesys.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {
    final CustomerMapper customerMapper;


    public CustomerServiceImpl(CustomerMapper customerMapper) {

        this.customerMapper = customerMapper;
    }

    @Override
    public List<Movie> findMovieByStatus() {
        return customerMapper.findMovieByStatus();
    }

    @Override
    public List<Ticket> findTicketBySid(Integer scheduleId) {
        return customerMapper.findTicketBySid(scheduleId);
    }

    @Override
    @Transactional
    public boolean updateTicket(int ticketId, Integer userId)  {
        // StopWatch watch = new StopWatch("买票耗时统计");
        //
        //
        // watch.start("查询版本号");
        final Integer versionByTid = customerMapper.findVersionByTid(ticketId);
        // watch.stop();

        if(versionByTid != 0) return false;

        // watch.start("更新ticket状态");
        boolean flag = customerMapper.updateTicket(ticketId,userId,versionByTid);
        // watch.stop();
        // log.info("\r\n" + watch.prettyPrint());
        return flag;
    }

    @Override
    public List<Schedule> findScheduleTicket(Integer movieId) {
        return customerMapper.findScheduleTicket(movieId);
    }

    @Override
    public  void  updateTicketBySync(Integer tid,Integer userId) {
        customerMapper.updateTicketSync(tid,userId);
    }
}
