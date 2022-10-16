package com.qf.moviesys.service;


import com.qf.moviesys.pojo.User;

public interface UserService {

    User checkLogin(User user);

    void insertUser(User user);


}
