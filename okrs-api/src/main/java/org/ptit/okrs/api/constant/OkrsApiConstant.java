package org.ptit.okrs.api.constant;

import static org.ptit.okrs.api.constant.OkrsApiConstant.ApiConstant.API_PREFIX;
import static org.ptit.okrs.api.constant.OkrsApiConstant.ApiConstant.API_VERSION;
import static org.ptit.okrs.api.constant.OkrsApiConstant.ResourceConstant.DAILY_PLAN;
import static org.ptit.okrs.api.constant.OkrsApiConstant.ResourceConstant.KEY_RESULT;
import static org.ptit.okrs.api.constant.OkrsApiConstant.ResourceConstant.OBJECTIVE;

public class OkrsApiConstant {
  public static class ApiConstant {
    public static final String API_PREFIX = "/api";
    public static final String API_VERSION = "/v1";
  }

  public static class ResourceConstant {
    public static final String OBJECTIVE = "/objectives";
    public static final String DAILY_PLAN = "/daily-plans";
    public static final String KEY_RESULT = "/key-results";
  }

  public static class BaseUrl {
    public static final String OBJECTIVE_BASE_URL = API_PREFIX + API_VERSION + OBJECTIVE;
    public static final String DAILY_PLAN_BASE_URL = API_PREFIX + API_VERSION + DAILY_PLAN;
    public static final String KEY_RESULT_BASE_URL = API_PREFIX + API_VERSION + KEY_RESULT;
  }
}
