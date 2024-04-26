package io.rasha.movie.info.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

  private static final Pattern NUMBER_PATTERN = Pattern.compile("^\\s*(\\d{4}).*");

  public static Optional<Integer> extractNumber(String text) {
    if (text == null || text.isEmpty()) {
      return Optional.empty();
    }

    // Regular expression for leading spaces, any characters, and a group capturing 4 digits
    Matcher matcher = NUMBER_PATTERN.matcher(text);

    if (!matcher.find()) {
      return Optional.empty();
    }

    // Extract the captured group (the 4-digit number)
    String numberString = matcher.group(1);
    return Optional.of(Integer.parseInt(numberString));
  }

  public static Boolean extractBoolean(String data) {
    return (data != null && data.strip().equalsIgnoreCase("yes")) ? Boolean.TRUE :
        (data != null && data.strip().equalsIgnoreCase("no")) ? Boolean.FALSE : null;
  }
}
