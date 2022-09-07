package com.example.guestbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // AuditingEntityListner 사용하기 위해.
public class GuestbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestbookApplication.class, args);
    }

}
