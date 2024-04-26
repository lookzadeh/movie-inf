package io.rasha.movie.info.util;

import static io.rasha.movie.info.util.Utils.extractBoolean;
import static io.rasha.movie.info.util.Utils.extractNumber;

import io.rasha.movie.info.dto.AwardDto;
import io.rasha.movie.info.model.Award;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Slf4j
public class CsvParserUtil {
  private final static String CSV_FILE_NAME = "awards.csv";
  private final static String FLAG_FILE_NAME = "data_imported.flag";

  public static List<Award> getAwardsFromCsvFile() {

    List<AwardDto> dataList = new ArrayList<>();

    try {
      // Load CSV file from resources folder
      ClassLoader classLoader = CsvParserUtil.class.getClassLoader();

      InputStream inputStream = classLoader.getResourceAsStream(CSV_FILE_NAME);
      if (inputStream == null) {
        log.error("CSV file not found: " + CSV_FILE_NAME);
        return null;
      }
      Reader reader = new BufferedReader(new InputStreamReader(inputStream));
      CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
      for (CSVRecord csvRecord : csvParser) {
        // Assuming CSV format is: Year,Category,Nominee,Additional Info,Won?
        Optional<Integer> year = extractNumber(csvRecord.get(0));
        if (year.isPresent() && isDataValid(csvRecord)) {
          String category = csvRecord.get(1);
          String nominee = csvRecord.get(2);
          String additionalInfo = csvRecord.get(3);
          AwardDto award = AwardDto.builder()
              .movieYear(year.get())
              .category(category.length() > 295 ? category.substring(0, 295) : category)
              .nominee(nominee.length() > 295 ? nominee.substring(0, 295) : nominee)
              .additionalInfo(additionalInfo.length() > 2995 ? additionalInfo.substring(0, 2995) : additionalInfo)
              .isWon(extractBoolean(csvRecord.get(4))).build();
          dataList.add(award);
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    // TODO: this method is for integrating awards with same Nominee in one award but if the business changed we can change it
    return integrateObjDtoList(dataList);
  }

  public static boolean hasAlreadyImported() {
    try {
      // Check if the flag file exists
      ClassLoader classLoader = CsvParserUtil.class.getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream(FLAG_FILE_NAME);
      return inputStream != null;
    } catch (Exception e) {
      // Ignore IO exceptions while checking the flag file
      return false;
    }
  }

  public static void createFlagFile() throws IOException {
    // Create an empty flag file to indicate successful import
    ClassLoader classLoader = CsvParserUtil.class.getClassLoader();
    FileWriter writer = new FileWriter(classLoader.getResource(FLAG_FILE_NAME).getPath());
    writer.write("");
    writer.close();
  }

  private static boolean isDataValid(CSVRecord csvRecord) {
    return
        csvRecord.get(1) != null && !csvRecord.get(1).isEmpty() && !csvRecord.get(1).isBlank() &&
            csvRecord.get(2) != null && !csvRecord.get(2).isEmpty() && !csvRecord.get(2).isBlank();
  }

  private static List<Award> integrateObjDtoList(List<AwardDto> awardDtoList) {
    // Use a HashMap for efficient lookups by name (key)
    Map<String, Award> integratedMap = new HashMap<>();

    // Process each awardDtoList
    for (AwardDto dto : awardDtoList) {
      String name = dto.getNominee();

      // Check if an Award with the same name exists in the map
      Award existAward = integratedMap.get(name);
      if (existAward == null ||
          (!existAward.getCategory().strip().equalsIgnoreCase("Best Picture") &&
              dto.getCategory().strip().equalsIgnoreCase("Best Picture") )) {
        Award award = new Award();
        award.setMovieYear(dto.getMovieYear());
        award.setCategory(dto.getCategory());
        award.setNominee(dto.getNominee());
        award.setAdditionalInfo(dto.getAdditionalInfo());
        award.setIsWon(dto.getIsWon());

        integratedMap.put(name, award);
      }
    }

    return new ArrayList<>(integratedMap.values());
  }

}
