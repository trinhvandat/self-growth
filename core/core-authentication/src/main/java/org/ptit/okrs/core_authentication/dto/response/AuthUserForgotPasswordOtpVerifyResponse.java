package org.ptit.okrs.core_authentication.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthUserForgotPasswordOtpVerifyResponse {
  private String resetPasswordKey;
}
