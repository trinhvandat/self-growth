package org.ptit.okrs.core_authentication.service;

import org.ptit.okrs.core_authentication.entity.AuthUser;

public interface AuthUserService {
  AuthUser findById(String id);
}
