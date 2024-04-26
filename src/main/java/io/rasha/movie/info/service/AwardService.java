package io.rasha.movie.info.service;

import static io.rasha.movie.info.util.CsvParserUtil.getAwardsFromCsvFile;

import io.rasha.movie.info.model.Award;
import io.rasha.movie.info.repository.AwardRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class AwardService {

  private final AwardRepository awardsRepository;
  private final SettingService settingService;

  @Transactional
  public void saveAwardsFromCSV() {
    if (settingService.isCSVImported()) {
      return;
    }

    try {
      // TODO: we can do it by pagination get and persist
      List<Award> awardList = getAwardsFromCsvFile();
      if (awardList != null && !awardList.isEmpty()) {
        awardsRepository.saveAll(awardList);
        // After successful import, create the flag file
        settingService.setCSVImported(true);
      }
    }
    catch (Exception e) {
      log.error("Error parsing CSV or saving awards: " + e.getMessage());
    }
  }

  public Optional<Award> findAwardByNominee(String nominee) {
    return awardsRepository.findByNominee(nominee);
  }
}

