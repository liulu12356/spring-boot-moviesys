package com.qf.moviesys.web;


import com.qf.moviesys.pojo.User;
import com.qf.moviesys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    //如果使用post提交，可以直接获取提交表单对应key的值
    @PostMapping("/user/login")
    User checkLogin(String username, String password){
        User user=new User(username,password);
        return  userService.checkLogin(user);
    }
}
