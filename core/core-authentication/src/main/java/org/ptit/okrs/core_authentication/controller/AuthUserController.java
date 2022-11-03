package org.ptit.okrs.core_authentication.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.dto.request.*;
import org.ptit.okrs.core_authentication.dto.response.AuthUserForgotPasswordOtpVerifyResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserLoginResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;
import org.ptit.okrs.core_authentication.facade.AuthFacadeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/auth/users")
@RestController
@Slf4j
public class AuthUserController {

  private final AuthFacadeService authFacadeService;

  public AuthUserController(AuthFacadeService authFacadeService) {
    this.authFacadeService = authFacadeService;
  }

  @ApiOperation("Step 2: User active account by otp")
  @ApiResponse(code = 200, message = "Successfully response.")
  @PostMapping("/active")
  @ResponseStatus(HttpStatus.OK)
  public void activeAccount(@Valid @RequestBody AuthUserActiveAccountRequest request) {
    log.info("(activeAccount)request: {}", request);
    authFacadeService.activeAccount(request);
  }

  @ApiOperation("Step 1: Create new user for sign up account")
  @ApiResponse(code = 201, message = "Successfully response.")
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthUserRegisterResponse register(@Valid @RequestBody AuthUserRegisterRequest request) {
    log.info("(createUser)request: {}", request);
    return authFacadeService.register(request);
  }

  @ApiOperation("User login")
  @ApiResponse(code = 200, message = "Successfully response.")
  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public AuthUserLoginResponse login(@Valid @RequestBody AuthUserLoginRequest request) {
    log.info("(login)request: {}", request);
    return authFacadeService.login(request);
  }

  @ApiOperation("User request forgot password")
  @ApiResponse(code = 200, message = "Successfully response.")
  @PostMapping("/forgot-password")
  @ResponseStatus(HttpStatus.OK)
  public void forgotPassword(@Valid @RequestBody AuthUserResetPasswordRequest request) {
    log.info("(forgotPassword)request: {}", request);
    authFacadeService.forgotPassword(request);
  }

  @ApiOperation("User verify otp forgot password")
  @ApiResponse(code = 200, message = "Successfully response.")
  @PostMapping("/forgot-password/otp-verify")
  @ResponseStatus(HttpStatus.OK)
  public AuthUserForgotPasswordOtpVerifyResponse verifyOtpForgotPassword(@Valid @RequestBody AuthUserForgotPasswordOtpVerifyRequest request) {
    log.info("(verifyOtpForgotPassword)request: {}", request);
    return authFacadeService.verifyOtpForgotPassword(request);
  }

  @ApiOperation("User reset password")
  @ApiResponse(code = 200, message = "Successfully response.")
  @PostMapping("/forgot-password/reset")
  @ResponseStatus(HttpStatus.OK)
  public void resetPassword(@Valid @RequestBody AuthUserForgotPasswordResetRequest request) {
    log.info("(resetPassword)request: {}", request);
    authFacadeService.resetPassword(request);
  }
}
