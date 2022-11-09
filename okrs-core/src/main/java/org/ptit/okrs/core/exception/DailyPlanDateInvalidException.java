package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.BadRequestException;

public class DailyPlanDateInvalidException extends BadRequestException {

  public DailyPlanDateInvalidException(String type) {
    setCode("org.ptit.okrs.core.exception.DailyPlanDateInvalidException");
    addParams("type", type);
  }
}
