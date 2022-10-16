package com.qf.moviesys.util;


import com.qf.moviesys.pojo.Ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketUtil {

    final static String [] LINE={"A","B","C","D","E","F","G","H","I","J","K"};
    //假设默认每行最多10个座位
     int MAX=10;


    public static List<Ticket> createTicket(int line, int column, Integer movieId, Integer scheduleId){
        List<Ticket> ticketList=new ArrayList<>();
        for (int i=0;i<line;i++){
                for (int j=0;j<column;j++){
                    String seq=LINE[i]+j;
                    Ticket ticket=new Ticket(null,movieId,scheduleId,seq,1,new Date(),null,null,null);
                    ticketList.add(ticket);
                }
        }
        return ticketList;
    }

    //若不传参，默认为5行10列
    public static List<Ticket> createTicket(Integer movieId,Integer scheduleId){
        List<Ticket> ticketList=new ArrayList<>();
        for (int i=0;i<5;i++){
            for (int j=0;j<10;j++){
                String seq=LINE[i]+j;
                Ticket ticket=new Ticket(null,movieId,scheduleId,seq,0,new Date(),null,null,null);
                ticketList.add(ticket);
            }
        }
        return ticketList;
    }
}
