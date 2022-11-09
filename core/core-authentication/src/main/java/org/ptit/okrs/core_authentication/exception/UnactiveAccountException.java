package org.ptit.okrs.core_authentication.exception;

import org.ptit.okrs.core_exception.ForbiddenException;

public class UnactiveAccountException extends ForbiddenException {

  public UnactiveAccountException(String userId) {
    super(userId);
    setCode("org.ptit.okrs.core_authentication.exception.UnactiveAccountException");
  }
}
