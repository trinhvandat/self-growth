package org.ptit.okrs.core_authentication.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthUserForgotPasswordOtpVerifyRequest {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String otp;
}
