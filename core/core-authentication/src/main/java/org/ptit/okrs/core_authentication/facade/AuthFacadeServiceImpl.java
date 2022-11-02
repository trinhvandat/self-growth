package org.ptit.okrs.core_authentication.facade;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.dto.request.AuthUserRegisterRequest;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class AuthFacadeServiceImpl implements AuthFacadeService {

  private final AuthAccountService authAccountService;
  private final AuthUserService authUserService;
  private final AuthTokenService authTokenService;
  private final PasswordEncoder passwordEncoder;

  public AuthFacadeServiceImpl(
      AuthAccountService authAccountService,
      AuthUserService authUserService,
      AuthTokenService authTokenService,
      PasswordEncoder passwordEncoder
  ) {
    this.authAccountService = authAccountService;
    this.authUserService = authUserService;
    this.authTokenService = authTokenService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public AuthUserRegisterResponse register(AuthUserRegisterRequest request) {
    log.info("(register)request: {}", request);

    request.validate();

    var authUser = authUserService.create(request.getEmail());
    var authAccount = authAccountService.create(
        authUser.getId(),
        request.getUsername(),
        passwordEncoder.encode(request.getPassword())
    );

    return AuthUserRegisterResponse.of(
        authUser.getId(),
        authUser.getEmail(),
        authAccount.getId(),
        authAccount.getUsername(),
        authAccount.getIsActivated()
    );
  }
}
