package com.example;

import com.example.controller.TodoController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackageClasses = TodoController.class)
public class RestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestapiApplication.class, args);
    }

}
