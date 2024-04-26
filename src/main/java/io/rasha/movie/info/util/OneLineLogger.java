package io.rasha.movie.info.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneLineLogger extends feign.Logger {
  @Override
  protected void log(String configKey, String format, Object... args) {
    // Format log message as a single line
    String message = String.format(configKey + " - " + format, args);
    log.info(message);
  }
}