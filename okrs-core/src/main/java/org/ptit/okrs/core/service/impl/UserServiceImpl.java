package org.ptit.okrs.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core.constant.Gender;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.exception.AvatarNotFoundException;
import org.ptit.okrs.core.exception.EmailAlreadyExistsException;
import org.ptit.okrs.core.exception.EmailInvalidException;
import org.ptit.okrs.core.exception.PhoneNumberInvalidException;
import org.ptit.okrs.core.model.UserCreateResponse;
import org.ptit.okrs.core.model.UserResponse;
import org.ptit.okrs.core.repository.UserRepository;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.core.service.base.impl.BaseServiceImpl;
import org.ptit.okrs.core_exception.InternalServerError;
import org.ptit.okrs.core_exception.NotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional
  public UserCreateResponse create(String name, String email) {
    log.info("(create)name: {}, email: {}", name, email);

    if (!email.contains(".")) {
      log.error("(create)email: {} invalid ", email);
      throw new EmailInvalidException(email);
    }

    if (repository.existsByEmail(email)) {
      log.error("(create)email: {} conflict data", email);
      throw new EmailAlreadyExistsException(email);
    }

    return UserCreateResponse.from(create(User.of(name, email)));
  }

  @Override
  @Transactional
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
      throw new PhoneNumberInvalidException(phone);
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
  @Transactional(readOnly = true)
  public UserResponse get(String userId) {
    log.debug("(get)userId: {}", userId);
    return UserResponse.from(find(userId));
  }

  @Override
  @Transactional
  public void changePathAvatar(String userId, String pathAvatar) {
    log.info("(changePathAvatar)userId: {}, pathAvatar: {}", userId, pathAvatar);
    if (!isExist(userId)) {
      log.error("(changePathAvatar)userId: {} not found", userId);
      throw new NotFoundException(userId, User.class.getSimpleName());
    }
    repository.updateAvatar(userId, pathAvatar);
  }

  @Override
  @Transactional(readOnly = true)
  public InputStreamResource getAvatar(String userId) {
    log.info("(getAvatar)userId: {}", userId);

    if (!isExist(userId)) {
      log.error("(getAvatar)userId: {} not found", userId);
      throw new NotFoundException(userId, User.class.getSimpleName());
    }

    var pathFile = repository.findAvatar(userId);
    if (Objects.isNull(pathFile)) {
      log.error("(getAvatar)userId: {} not found", userId);
      throw new AvatarNotFoundException(userId);
    }

    try {
      var file = new File(pathFile);
      return new InputStreamResource(new FileInputStream(file));
    } catch (Exception e) {
      log.error("(getAvatar)exception: {}", e.getMessage());
      throw new InternalServerError();
    }
  }
}
