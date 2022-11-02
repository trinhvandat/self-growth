package org.ptit.okrs.core.service.impl;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.Gender;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.exception.InputDataInvalidException;
import org.ptit.okrs.core.exception.ConflictDataException;
import org.ptit.okrs.core.model.UserCreateResponse;
import org.ptit.okrs.core.model.UserResponse;
import org.ptit.okrs.core.repository.UserRepository;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.NotFoundException;

@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public UserCreateResponse create(String name, String email) {
    log.info("(create)name: {}, email: {}", name, email);

    if (!email.contains(".")) {
      log.error("(create)email: {} invalid ", email);
      throw new InputDataInvalidException("email", User.class.getSimpleName());
    }

    if (repository.existsByEmail(email)) {
      log.error("(create)email: {} conflict data", email);
      throw new ConflictDataException(User.class.getSimpleName(), "email");
    }

    return UserCreateResponse.from(create(User.of(name, email)));
  }

  @Override
  public UserResponse update(
      String id,
      String name,
      String phone,
      Integer dateOfBirth,
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

    var regexPhoneNumber = "(0)(3|5|7|8|9)+([0-9]{8})\\b";
    if (!phone.matches(regexPhoneNumber)) {
      log.error("(update)phone: {} invalid ", phone);
      throw new InputDataInvalidException("phone", User.class.getSimpleName());
    }


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
  public String changePathAvatar(String userId, String pathAvatar) {
    log.info("(changeAvatar)userId: {}, avatar: {}", userId, pathAvatar);
    var user = find(userId);
    if (Objects.isNull(user)) {
      throw new NotFoundException(userId, User.class.getSimpleName());
    }

    user.setAvatar(pathAvatar);
     user = update(user);
    return user.getAvatar();
  }

  @Override
  public String getPathAvatar(String userId) {
    log.info("(getAvatar)userId: {}", userId);
    if(!isExist(userId)) {
      throw new NotFoundException(userId, User.class.getSimpleName());
    }
    return  repository.findAvatar(userId);
  }
}
