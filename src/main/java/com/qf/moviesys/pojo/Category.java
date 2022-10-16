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
@TableName("category")
public class Category {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String comment;
    private String state;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date modifiedTime;

}
