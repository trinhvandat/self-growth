package org.ptit.okrs.api.exception;

import org.ptit.okrs.core_exception.BadRequestException;

public class UnknownEnumException extends BadRequestException {

  public UnknownEnumException(String type, String value) {
    setCode("org.ptit.okrs.api.exception.UnknownEnumException");
    addParams("type", type);
    addParams("value", value);
  }
}
