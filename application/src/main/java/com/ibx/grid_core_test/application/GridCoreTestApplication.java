package com.ibx.grid_core_test.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(basePackages = "com.ibx.grid_core_test")
@SpringBootApplication(scanBasePackages = "com.ibx.grid_core_test")
public class GridCoreTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GridCoreTestApplication.class, args);
    }
}
