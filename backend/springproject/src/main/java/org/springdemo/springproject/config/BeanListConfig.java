//package org.springdemo.springproject.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class BeanListConfig implements CommandLineRunner {
//
//    private final ApplicationContext applicationContext;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//        for (String beanName : beanNames) {
//            log.info("Bean Name: {}", beanName);
//        }
//    }
//}
