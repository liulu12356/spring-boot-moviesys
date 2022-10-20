package com.qf.moviesys;

import com.qf.moviesys.dao.CustomerMapper;
import com.qf.moviesys.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
class MovieSysApplicationTests {

    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerMapper customerMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void useCASTest() throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    Random random=new Random();
                    int ticketId= random.nextInt(200);
                    customerService.updateTicket(ticketId,8);
                }
            });
        }
        Thread.sleep(100000);
    }

    @Test
    void tset(){
        customerService.updateTicket(20,8);
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testPassword(){
        String pwd = "admin";

        for (int i = 0; i < 1; i++) {
            String encode = passwordEncoder.encode(pwd);
            System.out.println(encode);
            System.out.println(passwordEncoder.matches(pwd, encode));
            // abc =>xyz-xyz   opq
        }
    }
}
