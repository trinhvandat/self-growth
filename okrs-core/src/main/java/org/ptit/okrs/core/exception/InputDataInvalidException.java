package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.BadRequestException;

public class InputDataInvalidException extends BadRequestException {

  public InputDataInvalidException(String fieldName, String objectName) {
    setCode("org.ptit.okrs.okrs-core.InputDataInvalidException");
    setStatus(400);
    addParams("fieldName", fieldName);
    addParams("objectName", objectName);
  }
}
