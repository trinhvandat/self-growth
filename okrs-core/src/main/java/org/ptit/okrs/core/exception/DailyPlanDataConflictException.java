package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.ConflictException;

public class DailyPlanDataConflictException extends ConflictException {

  public DailyPlanDataConflictException(String objectType, String objectDate) {
    setStatus(409);
    setCode("org.ptit.okrs.core.exception.DailyPlanDataConflict");
    addParams("objectType", objectType);
    addParams("objectDate", objectDate);
  }
}
