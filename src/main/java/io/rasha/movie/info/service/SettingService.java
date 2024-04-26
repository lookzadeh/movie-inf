package io.rasha.movie.info.service;

import static io.rasha.movie.info.util.CsvParserUtil.createFlagFile;
import static io.rasha.movie.info.util.CsvParserUtil.getAwardsFromCsvFile;
import static io.rasha.movie.info.util.CsvParserUtil.hasAlreadyImported;

import io.rasha.movie.info.model.Award;
import io.rasha.movie.info.model.Setting;
import io.rasha.movie.info.repository.AwardRepository;
import io.rasha.movie.info.repository.SettingRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SettingService {

  private final SettingRepository settingRepository;

  public SettingService(SettingRepository settingRepository) {
    this.settingRepository = settingRepository;
  }

  public boolean isCSVImported(){
    Optional<String> isCSVImported = getSetting("isCSVImported");
    return isCSVImported.isPresent() &&
        Boolean.parseBoolean(isCSVImported.get());
  }

  public void setCSVImported(boolean isCSVImported){
    updateSetting("isCSVImported", Boolean.TRUE.toString());
  }

  public Optional<String> getSetting(String name) {
    Optional<Setting> setting = settingRepository.findByName(name);
    return setting.map(Setting::getValue);
  }

  @Transactional
  public void updateSetting(String name, String value) {
    Setting setting = settingRepository.findByName(name).orElseGet(() -> {
      Setting newSetting = new Setting();
      newSetting.setName(name);
      return newSetting;
    });
    setting.setValue(value);
    settingRepository.save(setting);
  }
}

