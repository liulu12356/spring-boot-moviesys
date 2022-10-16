package com.qf.moviesys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.moviesys.pojo.User;

public interface UserMapper extends BaseMapper<User> {

    User checkLogin(User user);


}
