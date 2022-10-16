package com.qf.moviesys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("movie")
public class Movie {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String detail;
    private String path;
    private Integer status;
    private Integer state;
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private Date createdTime;
    @JsonFormat(pattern = "yy/MM/dd HH:mm:ss")
    private Date modifiedTime;

    // @TableField它用在属性上，表示该属性不为数据库表字段，但又是必须使用的。
    @TableField(exist = false)
    private List<Schedule> scheduleList;

    @TableField(exist = false)
    private List<Category> categoryList;

    @TableField(exist = false)
    private List<Integer> categoryIdList;




    public Movie(String title, String description, String detail,Integer state) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.state=state;
    }


}
