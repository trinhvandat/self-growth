package org.ptit.okrs.core_authentication.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthUserActiveAccountRequest {

  //TODO: LingTG add validate for email
  @Email
  @NotBlank
  private String email;

  //TODO: LinhTG add validate for otp
  @NotBlank
  private String otp;
}
