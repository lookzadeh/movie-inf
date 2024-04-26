package io.rasha.movie.info.util;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

  // Set the logging level
  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL; // Adjust this based on your requirement
  }

  // Register the custom logger
  @Bean
  public Logger logger() {
    return new OneLineLogger(); // Your custom logger
  }
}