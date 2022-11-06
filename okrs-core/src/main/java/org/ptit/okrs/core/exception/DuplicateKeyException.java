package org.ptit.okrs.core.exception;

import org.ptit.okrs.core_exception.BaseException;
import org.ptit.okrs.core_exception.ConflictException;

public class DuplicateKeyException extends ConflictException {

  public DuplicateKeyException(String message) {
    setStatus(409);
    setCode("org.ptit.okrs.core_exception.ConflictException");
    setMessage(message);
  }
}
