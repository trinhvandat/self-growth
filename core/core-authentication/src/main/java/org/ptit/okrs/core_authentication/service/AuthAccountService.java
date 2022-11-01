package org.ptit.okrs.core_authentication.service;

import org.ptit.okrs.core_authentication.entity.AuthAccount;

public interface AuthAccountService {
  AuthAccount findById(String id);
  AuthAccount findByUserIdWithThrow(String userId);

  AuthAccount create(String userId, String username, String password);
}
