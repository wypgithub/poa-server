package com.poa.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class POAServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(POAServerApplication.class, args);
    }

}
