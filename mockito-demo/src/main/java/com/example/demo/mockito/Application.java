package com.example.demo.mockito;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangtao
 * @since 2024/9/12 23:11
 */
@SpringBootApplication
@MapperScan(basePackages = "com.example.demo.mockito.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
