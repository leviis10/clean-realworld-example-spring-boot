package com.leviis.realworldexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class Application {

    private Application() {}

    static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
