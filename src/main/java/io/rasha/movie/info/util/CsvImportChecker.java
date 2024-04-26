package io.rasha.movie.info.util;

import io.rasha.movie.info.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CsvImportChecker implements ApplicationListener<ContextRefreshedEvent> {

  private final AwardService awardService;

  public CsvImportChecker(AwardService awardService) {
    this.awardService = awardService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // TODO: we can call it in a separate thread
    awardService.saveAwardsFromCSV();
  }
}
