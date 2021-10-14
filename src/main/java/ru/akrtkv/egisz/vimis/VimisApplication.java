package ru.akrtkv.egisz.vimis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class VimisApplication {

    public static void main(String[] args) {
        SpringApplication.run(VimisApplication.class, args);
    }
}