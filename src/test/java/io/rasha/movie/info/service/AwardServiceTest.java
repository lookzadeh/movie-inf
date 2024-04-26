package io.rasha.movie.info.service;

import static io.rasha.movie.info.util.CsvParserUtil.getAwardsFromCsvFile;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.rasha.movie.info.model.Award;
import io.rasha.movie.info.repository.AwardRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AwardServiceTest {

  @Mock
  private AwardRepository awardRepository;

  @Mock
  private SettingService settingService;

  private AwardService awardService;

  @BeforeEach
  public void setUp() {
    awardService = new AwardService(awardRepository, settingService);
  }

  @Test
  public void testSaveAwardsFromCSV_CsvNotImported_Success() throws Exception {
    List<Award> awards = createMockAwards();
    when(settingService.isCSVImported()).thenReturn(false);
    when(getAwardsFromCsvFile()).thenReturn(awards);

    awardService.saveAwardsFromCSV();

    verify(settingService).isCSVImported();
//    verify(getAwardsFromCsvFile()).calledOnce();
    verify(awardRepository).saveAll(awards);
    verify(settingService).setCSVImported(true);
  }

  @Test
  public void testSaveAwardsFromCSV_CsvNotImported_EmptyList() throws Exception {
    when(settingService.isCSVImported()).thenReturn(false);
    when(getAwardsFromCsvFile()).thenReturn(Collections.emptyList());

    awardService.saveAwardsFromCSV();

    verify(settingService).isCSVImported();
//    verify(getAwardsFromCsvFile()).calledOnce();
    verifyNoInteractions(awardRepository);
    verifyNoInteractions(settingService); // setCSVImported not called
  }

  @Test
  public void testSaveAwardsFromCSV_CsvAlreadyImported() throws Exception {
    when(settingService.isCSVImported()).thenReturn(true);

    awardService.saveAwardsFromCSV();

    verify(settingService).isCSVImported();
    verifyNoInteractions(getAwardsFromCsvFile());
    verifyNoInteractions(awardRepository);
    verifyNoInteractions(settingService); // setCSVImported not called
  }

  @Test(expected = Exception.class)
  public void testSaveAwardsFromCSV_Exception() throws Exception {
    when(settingService.isCSVImported()).thenReturn(false);
    when(getAwardsFromCsvFile()).thenThrow(new Exception("Error parsing CSV"));

    awardService.saveAwardsFromCSV();

    verify(settingService).isCSVImported();
//    verify(getAwardsFromCsvFile()).calledOnce();
    verifyNoInteractions(awardRepository);
    verify(settingService).setCSVImported(false); // Flag not set due to exception
  }

  @Test
  public void testFindAwardByNominee_ExistingNominee() {
    Optional<Award> expectedAward = Optional.of(new Award());
    when(awardRepository.findByNominee("John Doe")).thenReturn(expectedAward);

    Optional<Award> actualAward = awardService.findAwardByNominee("John Doe");

    assertEquals(expectedAward, actualAward);
    verify(awardRepository).findByNominee("John Doe");
  }

  @Test
  public void testFindAwardByNominee_NonExistingNominee() {
    when(awardRepository.findByNominee("Jane Doe")).thenReturn(Optional.empty());

    Optional<Award> actualAward = awardService.findAwardByNominee("Jane Doe");

    assertEquals(Optional.empty(), actualAward);
    verify(awardRepository).findByNominee("Jane Doe");
  }

  private List<Award> createMockAwards() {
    List<Award> awards = new ArrayList<>();
    Award a = new Award();
    a.setNominee("N1");
    a.setCategory("C1");
    a.setIsWon(false);
    awards.add(a);
    return awards;
  }
}
