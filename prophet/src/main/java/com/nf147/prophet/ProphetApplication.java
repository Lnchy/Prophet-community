package com.nf147.prophet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.nf147.prophet.dao")
public class ProphetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProphetApplication.class, args);
    }

}

