package org.ptit.okrs.core_authentication.facade;

import java.util.Base64;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.constant.MailConstant.MailForgotPassword;
import org.ptit.okrs.core_authentication.constant.MailConstant.MailResetPassword;
import org.ptit.okrs.core_authentication.dto.request.AuthUserActiveAccountRequest;
import org.ptit.okrs.core_authentication.dto.request.AuthUserForgotPasswordOtpVerifyRequest;
import org.ptit.okrs.core_authentication.dto.request.AuthUserForgotPasswordResetRequest;
import org.ptit.okrs.core_authentication.dto.request.AuthUserLoginRequest;
import org.ptit.okrs.core_authentication.dto.request.AuthUserRegisterRequest;
import org.ptit.okrs.core_authentication.dto.request.AuthUserResetPasswordRequest;
import org.ptit.okrs.core_authentication.dto.response.AuthUserForgotPasswordOtpVerifyResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserLoginResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;
import org.ptit.okrs.core_authentication.exception.OTPInvalidException;
import org.ptit.okrs.core_authentication.exception.OtpNotFoundException;
import org.ptit.okrs.core_authentication.exception.PasswordConfirmNotMatchException;
import org.ptit.okrs.core_authentication.exception.PasswordInvalidException;
import org.ptit.okrs.core_authentication.exception.ResetKeyInvalidException;
import org.ptit.okrs.core_authentication.exception.UnactiveAccountException;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.ptit.okrs.core_authentication.service.OtpService;
import org.ptit.okrs.core_authentication.service.ResetKeyService;
import org.ptit.okrs.core_authentication.service.TokenRedisService;
import org.ptit.okrs.core_authentication.util.CryptUtil;
import org.ptit.okrs.core_email.service.EmailService;
import org.ptit.okrs.core_util.GeneratorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class AuthFacadeServiceImpl implements AuthFacadeService {

  private final AuthAccountService authAccountService;
  private final AuthUserService authUserService;
  private final AuthTokenService authTokenService;
  private final OtpService otpService;
  private final TokenRedisService tokenRedisService;
  private final EmailService emailService;
  private final Long accessTokenLifeTime;
  private final Long refreshTokenLifeTime;
  private final ResetKeyService resetKeyService;
  private final PasswordEncoder passwordEncoder;

  @Value("${application.mail.template_send_otp.name}")
  private String template;

  @Value("${application.authentication.redis.otp_time_out}")
  private Integer otpTimeLife;

  public AuthFacadeServiceImpl(
      AuthAccountService authAccountService,
      AuthUserService authUserService,
      AuthTokenService authTokenService,
      OtpService otpService,
      TokenRedisService tokenRedisService,
      Long accessTokenLifeTime,
      Long refreshTokenLifeTime,
      EmailService emailService,
      ResetKeyService resetKeyService,
      PasswordEncoder passwordEncoder) {
    this.authAccountService = authAccountService;
    this.authUserService = authUserService;
    this.authTokenService = authTokenService;
    this.otpService = otpService;
    this.tokenRedisService = tokenRedisService;
    this.emailService = emailService;
    this.accessTokenLifeTime = accessTokenLifeTime;
    this.refreshTokenLifeTime = refreshTokenLifeTime;
    this.resetKeyService = resetKeyService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void activeAccount(AuthUserActiveAccountRequest request) {
    log.info("(activeAccount)request: {}", request);
    authUserService.validateExistedWithEmail(request.getEmail());

    var otpRedis = otpService.get(request.getEmail());
    if (Objects.isNull(otpRedis)) {
      throw new OtpNotFoundException(request.getOtp(), "Otp code has expired!");
    }
    if (Objects.equals(otpRedis, request.getOtp())) {
      authAccountService.activeByEmail(request.getEmail());
    } else {
      throw new OtpNotFoundException(request.getOtp(), "Invalid Otp code!");
    }
  }

  @Override
  public void authenticate(String username, String userId) {
    var user = authUserService.findById(userId);
    var usernamePasswordAuthToken =
        new UsernamePasswordAuthenticationToken(username, userId, new ArrayList<>());
    usernamePasswordAuthToken.setDetails(user);
    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
  }

  @Override
  @Transactional
  public AuthUserLoginResponse login(AuthUserLoginRequest request) {
    log.info("(login)request: {}", request);
    var accountUser = authAccountService.findByUsername(request.getUsername());
    if (!accountUser.getIsActivated()) {
      throw new UnactiveAccountException(accountUser.getUserId());
    }
    if (!CryptUtil.getPasswordEncoder().matches(request.getPassword(), accountUser.getPassword())) {
      log.error("(login)password : {} --> PasswordInvalidException", request.getPassword());
      throw new PasswordInvalidException();
    }
    String accessToken =
        authTokenService.generateAccessToken(
            accountUser.getUserId(), accountUser.getEmail(), accountUser.getUsername());
    String refreshToken =
        authTokenService.generateRefreshToken(
            accountUser.getUserId(), accountUser.getEmail(), accountUser.getUsername());
    tokenRedisService.set(accountUser.getUserId(), "accessToken", accessToken);
    tokenRedisService.set(accountUser.getUserId(), "refreshToken", refreshToken);
    authenticate(accountUser.getUsername(), accountUser.getUserId());
    return AuthUserLoginResponse.from(
        accessToken, refreshToken, accessTokenLifeTime, refreshTokenLifeTime);
  }

  @Override
  @Transactional
  public AuthUserRegisterResponse register(AuthUserRegisterRequest request) {
    log.info("(register)request: {}", request);

    request.validate();

    // create account
    var authUser = authUserService.create(request.getEmail());
    var authAccount =
        authAccountService.create(
            authUser.getId(),
            request.getUsername(),
            CryptUtil.getPasswordEncoder().encode(request.getPassword()));

    // generate otp and push it to redis
    var otpActiveAccount = GeneratorUtils.generateOtp();
    otpService.set(authUser.getEmail(), otpActiveAccount);

    // Send mail request active account
    var param = new HashMap<String, Object>();
    param.put("time_life", otpTimeLife);
    param.put("otp", otpActiveAccount);
    emailService.send("OKRS: Register Account!", request.getEmail(), template, param);

    return AuthUserRegisterResponse.of(
        authUser.getId(),
        authUser.getEmail(),
        authAccount.getId(),
        authAccount.getUsername(),
        authAccount.getIsActivated());
  }

  @Override
  public void forgotPassword(AuthUserResetPasswordRequest request) {
    log.info("(forgotPassword)request: {}", request);
    // Step 1: validate exist identifier
    authUserService.validateExistedWithEmail(request.getEmail());

    // Step 2: if identifier found -> generate otp, push redis and send email verify
    // generate otp and push it to redis
    var otpForgotPassword = GeneratorUtils.generateOtp();
    otpService.set(request.getEmail(), otpForgotPassword);

    // send mail verify
    var params = new HashMap<String, Object>();
    params.put(MailForgotPassword.KEY_PARAM_OTP_TIME_LIFE, otpTimeLife);
    params.put(MailForgotPassword.KEY_PARAM_OTP, otpForgotPassword);
    emailService.send(
        MailForgotPassword.SUBJECT, request.getEmail(), MailForgotPassword.TEMPLATE_NAME, params);
  }

  @Override
  public AuthUserForgotPasswordOtpVerifyResponse verifyOtpForgotPassword(
      AuthUserForgotPasswordOtpVerifyRequest request) {
    log.info("(verifyOtpForgotPassword)request: {}", request);

    // chek email exist
    authUserService.validateExistedWithEmail(request.getEmail());

    // verify otp
    var otpCache = otpService.get(request.getEmail());
    if (!otpCache.equals(request.getOtp())) {
      log.error("(verifyOtpForgotPassword)OTP: {} invalid", request.getOtp());
      throw new OTPInvalidException();
    }

    // generate reset password key, push redis(key: email, value: resetKey), return to client
    String resetPasswordKey = Base64.getEncoder().encodeToString(request.getEmail().getBytes());
    resetKeyService.set(request.getEmail(), "email", resetPasswordKey);

    return new AuthUserForgotPasswordOtpVerifyResponse(resetPasswordKey);
  }

  @Override
  public void resetPassword(AuthUserForgotPasswordResetRequest request) {
    log.info("(resetPassword)request: {}", request);

    if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
      log.error(
          "(resetPassword)newPassword: {}, newPasswordConfirm:{}  don't match",
          request.getNewPassword(),
          request.getNewPasswordConfirm());
      throw new PasswordConfirmNotMatchException();
    }

    // validate email not found
    authUserService.validateExistedWithEmail(request.getEmail());

    // validate resetKey
    if (!resetKeyService
        .get(request.getEmail(), MailResetPassword.FIELD_KEY_CACHE_OF_EMAIL)
        .equals(request.getResetPasswordKey())) {
      log.error("(resetPassword)resetKey: {} invalid", request.getResetPasswordKey());
      throw new ResetKeyInvalidException();
    }

    // update new password
    authAccountService.updatePasswordByEmail(
        request.getEmail(), passwordEncoder.encode(request.getNewPassword()));
  }
}
