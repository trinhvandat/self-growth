package org.ptit.okrs.core_authentication.facade;

import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.dto.request.*;
import org.ptit.okrs.core_authentication.dto.response.AuthUserForgotPasswordOtpVerifyResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserLoginResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.ptit.okrs.core_authentication.service.OtpService;
import org.ptit.okrs.core_email.service.EmailService;
import org.ptit.okrs.core_util.GeneratorUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
public class AuthFacadeServiceImpl implements AuthFacadeService {

  private final AuthAccountService authAccountService;
  private final AuthUserService authUserService;
  private final AuthTokenService authTokenService;
  private final PasswordEncoder passwordEncoder;
  private final OtpService otpService;
  private final EmailService emailService;

  public AuthFacadeServiceImpl(
      AuthAccountService authAccountService,
      AuthUserService authUserService,
      AuthTokenService authTokenService,
      PasswordEncoder passwordEncoder,
      OtpService otpService,
      EmailService emailService
  ) {
    this.authAccountService = authAccountService;
    this.authUserService = authUserService;
    this.authTokenService = authTokenService;
    this.passwordEncoder = passwordEncoder;
    this.otpService = otpService;
    this.emailService = emailService;
  }

  @Override
  public void activeAccount(AuthUserActiveAccountRequest request) {
    log.info("(activeAccount)request: {}", request);
    //Step 1: validate email exist? Email have an account is de-active?
    //Step 2: validate otp
      // 2.1: otp expired
      // 2.2: otp invalid
    //step 3: if request valid, active account -> update isActivated of account = true.
  }

  @Override
  public AuthUserLoginResponse login(AuthUserLoginRequest request) {
    log.info("(login)request: {}", request);
    //Step 1: validate user and account not found;
    //Step 2: validate username and password
    //step 3: generate accessToken, refreshToken and push it to redis with its lifetime.
    //step 4: set security context holder: can see in the TokenAuthenticationFilter.
    //step 5: build response and return
    return new AuthUserLoginResponse();
  }

  @Override
  @Transactional
  public AuthUserRegisterResponse register(AuthUserRegisterRequest request) {
    log.info("(register)request: {}", request);

    request.validate();

    // create account
    var authUser = authUserService.create(request.getEmail());
    var authAccount = authAccountService.create(
        authUser.getId(),
        request.getUsername(),
        passwordEncoder.encode(request.getPassword())
    );

    //generate otp and push it to redis
    var otpActiveAccount = GeneratorUtils.generateOtp();
    otpService.set(authUser.getEmail(), otpActiveAccount);

    //Send mail request active account
    //TODO: ToanNS implement send mail with html
    emailService.send("subject", "to", "template", new HashMap<>());

    return AuthUserRegisterResponse.of(
        authUser.getId(),
        authUser.getEmail(),
        authAccount.getId(),
        authAccount.getUsername(),
        authAccount.getIsActivated()
    );
  }

  @Override
  public void forgotPassword(AuthUserResetPasswordRequest request) {
    log.info("(forgotPassword)request: {}", request);
    //Step 1: validate exist identifier
    //Step 2: if identifier found -> generate otp, push redis and send email verify
  }

  @Override
  public AuthUserForgotPasswordOtpVerifyResponse verifyOtpForgotPassword(AuthUserForgotPasswordOtpVerifyRequest request) {
    log.info("(verifyOtpForgotPassword)request: {}", request);
    //chek email exist
    //verify otp

    //generate reset password key, push redis(key: email, value: resetKey), return to client
    return new AuthUserForgotPasswordOtpVerifyResponse();
  }

  @Override
  public void resetPassword(AuthUserForgotPasswordResetRequest request) {
    log.info("(resetPassword)request: {}", request);
    // validate email not found
    // validate resetKey
    // update new password
  }
}
