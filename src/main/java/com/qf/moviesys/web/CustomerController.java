package com.qf.moviesys.web;


import com.qf.moviesys.pojo.Movie;
import com.qf.moviesys.pojo.Schedule;
import com.qf.moviesys.pojo.Ticket;
import com.qf.moviesys.service.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

@RestController
@CrossOrigin
@Log4j2
public class CustomerController {


    final CustomerService customerService;



    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customerMovie")
    List<Movie> findMovieByStatus(){
        return customerService.findMovieByStatus();
    }

    @GetMapping("/customer/schedule/{mid}")
    List<Schedule> findScheduleTicket(@PathVariable("mid") Integer movieId){
        return  customerService.findScheduleTicket(movieId);
    }

    @GetMapping("/ticket/{scheduleId}")
    List<Ticket> findTicketBySid(@PathVariable("scheduleId") Integer scheduleId){
       return  customerService.findTicketBySid(scheduleId);
    }

    @PostMapping("/ticket/buy/{ticketIdList}/{userId}")
    String BuyTicket(@PathVariable("ticketIdList") int [] ticketIdList
            ,@PathVariable("userId") Integer userId){

        System.out.println(userId);
        for (int i = 0; i < ticketIdList.length; i++) {
            customerService.updateTicket(ticketIdList[i],userId);
        }
        return "BuyOK";
    }


    @GetMapping("/test/cas")
    void testCAS(int count){

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(count);
        Random random=new Random();
        StopWatch stopWatch = new StopWatch("乐观锁买票耗时统计");
        for (int i = 0; i < count; i++) {
           Thread t =new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       //阻塞线程，当所有线程全部创建并就绪再唤醒
                       start.await();
                       customerService.updateTicket(random.nextInt(400)+1,8);
                       System.out.println(Thread.currentThread().getName());
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }finally {
                       end.countDown();
                   }
               }
           });
           t.start();

        }

        stopWatch.start("更新状态");
        //唤醒所有线程同一时间开始工作
        start.countDown();

        try {
            //主线程阻塞,等待其他所有 worker 线程完成后再执行
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println("主程序结束");
    }


    @GetMapping("/test/sync")
    void  testSync(int count){
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(count);
        Random random=new Random();
        StopWatch stopWatch = new StopWatch("悲观锁买票耗时统计");
        for (int i = 0; i < count; i++) {
            Thread t =new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //阻塞线程，当所有线程全部创建并就绪再唤醒
                        start.await();
                        synchronized(this) {
                            customerService.updateTicketBySync(random.nextInt(400) + 1, 8);
                        }
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        end.countDown();
                    }
                }
            });
            t.start();

        }
        stopWatch.start("更新状态");
        //唤醒所有线程同一时间开始工作
        start.countDown();

        try {
            //主线程阻塞,等待其他所有 worker 线程完成后再执行
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println("主程序结束");
    }



}
