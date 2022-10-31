package org.ptit.okrs.core.service.impl;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.Gender;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.model.UserResponse;
import org.ptit.okrs.core.repository.UserRepository;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.ConflictException;

@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;
  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public UserResponse create(String name, String email) {
    log.info("(create)name: {}, email: {}", name, email);

    if (repository.existsByEmail(email)) {
      log.error("(create)email: {} conflict ", email);
      throw new ConflictException();
    }

    return UserResponse.from(create(User.of(name, email)));
  }

  @Override
  public UserResponse update(
      String userId,
      String name,
      String phone,
      String email,
      Long dateOfBirth,
      Gender gender,
      String address) {
    log.info(
        "(update)userId: {}, name: {}, phone: {}, email: {}, dateOfBirth: {}, gender: {}, address: {}",
        userId,
        name,
        phone,
        email,
        dateOfBirth,
        gender,
        address);
    return null;
  }

  @Override
  public UserResponse get(String userId) {
    log.info("(get)userId: {}", userId);
    return null;
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
