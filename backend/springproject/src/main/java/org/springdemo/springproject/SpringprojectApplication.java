package org.springdemo.springproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableAspectJAutoProxy //enables Aspect-Oriented Programming (AOP) support in Spring
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }

}