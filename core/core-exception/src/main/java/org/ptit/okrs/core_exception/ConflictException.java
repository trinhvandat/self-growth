package org.ptit.okrs.core_exception;

public class ConflictException extends BaseException {
  public ConflictException() {
    setStatus(409);
    setCode("org.ptit.okrs.core_exception.ConflictException");
  }
  public ConflictException(String objectType, Long objectDate) {
    setStatus(409);
    setCode("org.ptit.okrs.okrs-core.ConflictDataException");
    addParams("objectType", objectType);
    addParams("objectDate", String.valueOf(objectDate));
  }
}
