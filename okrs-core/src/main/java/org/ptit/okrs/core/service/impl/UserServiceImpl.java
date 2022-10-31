package org.ptit.okrs.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.Gender;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.model.UserResponse;
import org.ptit.okrs.core.repository.UserRepository;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;
import org.ptit.okrs.core_exception.ConflictException;
import org.ptit.okrs.core_exception.NotFoundException;

@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public UserResponse create(String accountId, String name, String email) {
    log.info("(create)accountId: {}, name: {}, email: {}", accountId, name, email);

    if (repository.existsByAccountId(accountId)) {
      log.error("(create)accountId: {} conflict", accountId);
      throw new ConflictException();
    }

    if (repository.existsByEmail(email)) {
      log.error("(create)email: {} conflict ", email);
      throw new ConflictException();
    }

    return UserResponse.from(create(User.of(name, email, accountId)));
  }

  @Override
  public UserResponse update(
      String id,
      String name,
      String phone,
      Long dateOfBirth,
      Gender gender,
      String address) {
    log.info(
        "(update)id: {}, name: {}, phone: {}, dateOfBirth: {}, gender: {}, address: {}",
        id,
        name,
        phone,
        dateOfBirth,
        gender,
        address);
    var userUpdate = find(id);
    if (Objects.isNull(userUpdate)) {
      log.info("(update)id: {} not found", id);
      throw new NotFoundException(id, User.class.getSimpleName());
    }

    userUpdate.setName(name);
    userUpdate.setPhone(phone);
    userUpdate.setDateOfBirth(dateOfBirth);
    userUpdate.setGender(gender);
    userUpdate.setAddress(address);
    return UserResponse.from(update(userUpdate));

  }

  @Override
  public UserResponse get(String userId) {
    log.info("(get)userId: {}", userId);

    return repository
        .find(userId)
        .orElseThrow(
            () -> {
              throw new NotFoundException(userId, User.class.getSimpleName());
            });
  }

  @Override
  public String changeAvatar(String userId, String avatar) {
    log.info("(changeAvatar)userId: {}, avatar: {}", userId, avatar);
    return null;
  }

  @Override
  public String getAvatar(String userId) {
    log.info("(getAvatar)userId: {}", userId);
    return null;
  }
}
