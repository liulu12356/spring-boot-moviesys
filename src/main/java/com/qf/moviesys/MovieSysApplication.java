package com.qf.moviesys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.qf.moviesys.dao")
public class MovieSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieSysApplication.class, args);
    }

}
