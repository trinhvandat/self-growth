package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.ConflictException;

public class ConflictDataException extends ConflictException {

  public ConflictDataException(String objectName, String typeName) {
    setStatus(409);
    setCode("org.ptit.okrs.okrs-core.ConflictDataException");
    addParams("objectName", objectName);
    addParams("typeName", typeName);
  }
}
