package io.rasha.movie.info;

import static io.rasha.movie.info.util.Utils.extractNumber;
import static org.junit.jupiter.api.Assertions.*;

import io.rasha.movie.info.util.Utils;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UtilsTest {

  @Test
  public void extractNumber_NullInput_optionalEmpty() {
    Optional<Integer> extractedNumber = extractNumber(null);
    assertFalse(extractedNumber.isPresent());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "",
      "  1",
      "No number in this string.",
      "The number is missing."
  })
  public void extractNumber_EmptyInput_optionalEmpty(String input) {
    Optional<Integer> extractedNumber = extractNumber(input);
    assertFalse(extractedNumber.isPresent());
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "1234",
      "1234 (83rd)",
      "  1234 more text ",
      "1234number",
      "1234(123456)",
      "1234(number",
      "1234(number)",
      "1234number)"
  })
  public void extractNumber_ValidNumber_parsedCorrectly(String input) {
    Optional<Integer> extractedNumber = extractNumber(input);
    assertTrue(extractedNumber.isPresent());
    assertEquals(1234, extractedNumber.get());
  }

  @Test
  public void extractBoolean_NullInput_optionalEmpty() {
    assertNull(Utils.extractBoolean(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "",
      "  ",
      " 1 ",
      "four",
      "0",
      ".."
  })
  public void extractBoolean_EmptyInput_optionalEmpty(String input) {
    assertNull(Utils.extractBoolean(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "yes",
      "  Yes ",
      "YES "
  })
  public void extractBoolean_ValidBooleanTrue_parsedCorrectly(String input) {
    assertTrue(Utils.extractBoolean(input));
  }

  @ParameterizedTest
  @ValueSource(strings = {
      "no",
      "  NO ",
      "No "
  })
  public void extractBoolean_ValidBooleanFalse_parsedCorrectly(String input) {
    assertFalse(Utils.extractBoolean(input));
  }


}