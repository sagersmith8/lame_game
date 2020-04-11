package com.github.jsappz.lamegame.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    RuntimeOptions options() {
        return new RuntimeOptionsImpl();
    }
}
