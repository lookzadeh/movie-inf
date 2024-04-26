package io.rasha.movie.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MovieInfoApplication {

    public static void main(String args[]) {
        SpringApplication.run(MovieInfoApplication.class, args);
    }
}
