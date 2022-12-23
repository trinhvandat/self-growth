package org.ptit.okrs.api.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ptit.okrs.api.config.ApiTestConfig;
import org.ptit.okrs.core.entity.User;
import org.ptit.okrs.core.repository.UserRepository;
import org.ptit.okrs.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = {ApiTestConfig.class})
@ExtendWith(SpringExtension.class)
@Slf4j
@ActiveProfiles("dattv-dev")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TestUserService {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  public void clearData() {
    userRepository.deleteAll();
  }

  @AfterEach
  public void clearAfter() {
    userRepository.deleteAll();
  }

  @Test
  public void testCreateUser() {
    var user = new User();
    user.setEmail("trinhvandat90399@gmail.com");
    user.setName("Leonard Trinh");
    user = userService.create(user);

    var checkUser = userService.find(user.getId());
    Assertions.assertEquals(user.getName(), checkUser.getName());
  }
}
