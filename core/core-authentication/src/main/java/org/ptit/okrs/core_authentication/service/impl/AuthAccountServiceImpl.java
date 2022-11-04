package org.ptit.okrs.core_authentication.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.entity.AuthAccount;
import org.ptit.okrs.core_authentication.entity.AuthUser;
import org.ptit.okrs.core_authentication.exception.UserAlreadyHasAccountException;
import org.ptit.okrs.core_authentication.exception.UsernameAlreadyExistedException;
import org.ptit.okrs.core_authentication.repository.AuthAccountRepository;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_exception.NotFoundException;

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

  @Override
  public AuthAccount findByUserIdWithThrow(String userId) {
    log.debug("(findByUserIdWithThrow)userId: {}", userId);
    return repository.findFirstByUserId(userId)
        .orElseThrow(() -> {
          log.error("(findByUserIdWithThrow)userId: {} not found", userId);
          throw new NotFoundException(userId, AuthUser.class.getSimpleName());
        });
  }

  @Override
  public AuthAccount create(String userId, String username, String password) {
    log.info("(create)userId: {}, username: {}", userId, username);

    if (repository.existsByUserId(userId)) {
      log.error("(create)userId: {} already has account", userId);
      throw new UserAlreadyHasAccountException();
    }

    if (repository.existsByUsername(username)) {
      log.error("(create)username: {} already exists", username);
      throw new UsernameAlreadyExistedException(username);
    }

    return repository.save(AuthAccount.of(userId, username, password));
  }
}
