package com.qf.moviesys.serviceImpl;


import com.qf.moviesys.dao.UserMapper;
import com.qf.moviesys.pojo.User;
import com.qf.moviesys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User checkLogin(User user) {
        return userMapper.checkLogin(user);
    }

    @Override
    public void insertUser(User user) {

    }

}
