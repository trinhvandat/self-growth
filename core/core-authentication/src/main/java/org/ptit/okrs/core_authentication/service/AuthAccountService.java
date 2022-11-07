package org.ptit.okrs.core_authentication.service;

import org.ptit.okrs.core_authentication.entity.AuthAccount;
import org.ptit.okrs.core_authentication.repository.AccountUserProjection;

public interface AuthAccountService {
  AuthAccount findById(String id);
  AuthAccount findByUserIdWithThrow(String userId);

  /**
   * find info to validate user
   * @param username - username of account
   * @return a project object contain info to validate user
   */
  AccountUserProjection findByUsername(String username);
  AuthAccount create(String userId, String username, String password);

  void activeByEmail(String email);
}
