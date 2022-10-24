package org.ptit.okrs.core_authentication.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.entity.AuthAccount;
import org.ptit.okrs.core_authentication.repository.AuthAccountRepository;
import org.ptit.okrs.core_authentication.service.AuthAccountService;

@Slf4j
public class AuthAccountServiceImpl implements AuthAccountService {

  private final AuthAccountRepository repository;

  public AuthAccountServiceImpl(AuthAccountRepository repository) {
    this.repository = repository;
  }

  @Override
  public AuthAccount findById(String id) {
    if (log.isDebugEnabled())log.debug("(findById)id: {}", id);
    return repository.findById(id).orElse(null);
  }
}
