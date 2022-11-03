package org.ptit.okrs.core_authentication.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AuthUserForgotPasswordResetRequest {
  @NotBlank
  private String resetPasswordKey;

  private String email;
  private String newPassword;
  private String newPasswordConfirm;
}
