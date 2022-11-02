package org.ptit.okrs.core_authentication.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.entity.AuthUser;
import org.ptit.okrs.core_authentication.exception.EmailAlreadyExistException;
import org.ptit.okrs.core_authentication.repository.AuthUserRepository;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.ptit.okrs.core_exception.NotFoundException;

@Slf4j
public class AuthUserServiceImpl implements AuthUserService {

  private final AuthUserRepository repository;

  public AuthUserServiceImpl(AuthUserRepository repository) {
    this.repository = repository;
  }

  @Override
  public AuthUser findById(String id) {
    if (log.isDebugEnabled()) log.debug("(findById)id: {}", id);
    return repository.findById(id).orElseGet(() -> {
      log.error("(findById)id: {} not found", id);
      throw new NotFoundException(id, AuthUser.class.getSimpleName());
    });
  }

  @Override
  public AuthUser create(String email) {
    log.info("(create)email: {}", email);

    if (repository.existsByEmail(email)) {
      log.error("(create)email: {} already exist", email);
      throw new EmailAlreadyExistException(email);
    }

    return repository.save(AuthUser.from(email, email));
  }
}
