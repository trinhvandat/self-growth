package org.ptit.okrs.test.util;

import org.ptit.okrs.core.constant.DailyPlanStatus;
import org.ptit.okrs.core.entity.DailyPlan;
import org.ptit.okrs.core.entity.User;

public class PrepareDataUtils {
  public static User createUser() {
    var user = new User();
    user.setId("userIdTest1");
    user.setEmail("dat@gmail.com");
    user.setName("Leonard");
    return user;
  }

  public static DailyPlan createDailyPlan() {
    var dailyPlan = new DailyPlan();
    dailyPlan.setId("dailyPlan-test-1");
    dailyPlan.setTitle("test title");
    dailyPlan.setDescription("test description");
    dailyPlan.setStatus(DailyPlanStatus.TODO);
    dailyPlan.setDate(20221225);
    dailyPlan.setUserId("userIdTest1");
    return dailyPlan;
  }

  public static String clearUser() {
    return "userIdTest1";
  }

  public static String clearDailyPlan() {
    return "dailyPlan-test-1";
  }
}
