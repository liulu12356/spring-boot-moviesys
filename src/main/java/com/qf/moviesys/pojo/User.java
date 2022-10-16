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
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer userId;
    private String username;
    private String password;
    private Integer role;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
