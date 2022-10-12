package com.gengxiankun.familycapitalpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xiankun.geng
 */
@SpringBootApplication
@EnableScheduling
public class FamilyCapitalPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyCapitalPoolApplication.class, args);
    }

}
