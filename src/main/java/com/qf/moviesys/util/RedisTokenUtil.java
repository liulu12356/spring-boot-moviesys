package com.qf.moviesys.util;


import com.qf.moviesys.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisTokenUtil {

    @Autowired
     RedisTemplate<String,Object> redisTemplate;

    public  void setToken(String token, UserInfo userInfo){
        redisTemplate.opsForValue().set(token,userInfo);
    }

    public  UserInfo get(String token)throws RuntimeException{
        UserInfo info=null;
        try{
            info=(UserInfo) redisTemplate.opsForValue().get(token);}
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return info;
    }
}
