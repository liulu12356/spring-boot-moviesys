package com.qf.moviesys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.moviesys.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<UserInfo> {

    UserInfo selectByUsername(@Param("username") String username);
}
