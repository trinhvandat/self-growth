package org.ptit.okrs.core_authentication.facade;

import org.ptit.okrs.core_authentication.dto.request.*;
import org.ptit.okrs.core_authentication.dto.response.AuthUserForgotPasswordOtpVerifyResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserLoginResponse;
import org.ptit.okrs.core_authentication.dto.response.AuthUserRegisterResponse;

public interface AuthFacadeService {
  //TODO: LinhTG add java doc
  void activeAccount(AuthUserActiveAccountRequest request);

  AuthUserLoginResponse login(AuthUserLoginRequest request);

  AuthUserRegisterResponse register(AuthUserRegisterRequest request);

  void forgotPassword(AuthUserResetPasswordRequest request);

  AuthUserForgotPasswordOtpVerifyResponse verifyOtpForgotPassword(AuthUserForgotPasswordOtpVerifyRequest request);

  void resetPassword(AuthUserForgotPasswordResetRequest request);
}
