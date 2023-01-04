package org.ptit.okrs.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ptit.okrs.api.OkrsApiApplication;
import org.ptit.okrs.api.controller.DailyPlanController;
import org.ptit.okrs.api.model.response.OkrsResponse;
import org.ptit.okrs.core.configuration.JpaOkrsTransactionConfiguration;
import org.ptit.okrs.core.configuration.OkrsCoreConfiguration;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.test.util.PrepareDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import static org.ptit.okrs.test.util.DomainUtils.getLocalDomain;

@SpringBootTest(classes = OkrsApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Slf4j
@ContextConfiguration(classes = {
    OkrsCoreConfiguration.class,
    DailyPlanController.class,
    JpaOkrsTransactionConfiguration.class
})
public class TestDailyPlanController {

  @Autowired
  private DailyPlanController dailyPlanController;

  @Autowired
  private DailyPlanService dailyPlanService;

  @Autowired
  private UserService userService;

  @Autowired
  private RestTemplate restTemplate;

  @BeforeEach
  public void prepareData() {
    userService.create(PrepareDataUtils.createUser());
    dailyPlanService.create(PrepareDataUtils.createDailyPlan());
  }

  @AfterEach
  public void clearData() {
    dailyPlanService.delete(PrepareDataUtils.clearDailyPlan());
    userService.delete(PrepareDataUtils.clearUser());
  }

  @Test
  public void contextLoads() throws Exception {
    Assertions.assertNotNull(dailyPlanController);
  }

  @Test
  public void testGetDailyPlan() {
    var response = restTemplate.getForEntity(
        getLocalDomain("8081") + "/api/v1/daily-plans" + "/" + "dailyPlan-test-1", OkrsResponse.class
    );
    Assertions.assertNotNull(response);
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(((DailyPlan) response.getBody().getData()).getTitle(), "test title");
  }
}
