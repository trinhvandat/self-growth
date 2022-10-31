package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.ConflictException;

public class OkrsCoreConflictException extends ConflictException {

  public OkrsCoreConflictException(String objectName, String typeName) {
    setStatus(409);
    setCode("org.ptit.okrs.okrs-core.OkrsCoreConflictException");
    addParams("objectName", objectName);
    addParams("typeName", typeName);
  }
}
