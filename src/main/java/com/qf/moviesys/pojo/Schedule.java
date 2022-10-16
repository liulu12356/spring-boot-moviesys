package com.qf.moviesys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("schedule")
public class Schedule {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer movieId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date end;
    private Integer status;

    public Schedule(Date start, Date end,Integer movieId) {

        this.start=start;
        this.end=end;
        this.movieId=movieId;
    }
}
