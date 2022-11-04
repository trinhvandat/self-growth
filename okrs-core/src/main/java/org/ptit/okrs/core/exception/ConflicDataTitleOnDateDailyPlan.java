package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.ConflictException;

public class ConflicDataTitleOnDateDailyPlan extends ConflictException {

  public ConflicDataTitleOnDateDailyPlan(String objectType, Integer objectDate) {
    setStatus(409);
    setCode("org.ptit.okrs.okrs-core.ConflictDataException");
    addParams("objectType", objectType);
    addParams("objectDate", String.valueOf(objectDate));
  }
}
