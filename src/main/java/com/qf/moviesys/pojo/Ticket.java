package com.qf.moviesys.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ticket")
public class Ticket {
    @TableId(type = IdType.AUTO)
    private Integer tid;
    private Integer movieId;
    private Integer scheduleId;
    private String sequenceNo;
    private Integer status;
    private Date  createdTime;
    private Date  modifiedTime;
    private Integer userId;
    private Date buyTime;
}
