package org.ptit.okrs.api.constant;

import static org.ptit.okrs.api.constant.OkrsApiConstant.ApiConstant.*;
import static org.ptit.okrs.api.constant.OkrsApiConstant.ResourceConstant.*;

public class OkrsApiConstant {
  public static class ApiConstant {
    public static final String API_PREFIX = "/api";
    public static final String API_VERSION = "/v1";
  }

  public static class ResourceConstant {
    public static final String OBJECTIVE = "/objectives";
    public static final String DAILY_PLAN = "/daily-plans";
  }

  public static class BaseUrl {
    public static final String DAILY_PLAN_BASE_URL = API_PREFIX + API_VERSION + DAILY_PLAN;
  }
}
