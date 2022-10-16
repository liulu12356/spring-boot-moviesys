package com.qf.moviesys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("movie_category")
public class MovieCategory {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer MovieId;
    private Integer categoryId;
}
