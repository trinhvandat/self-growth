package org.ptit.okrs.core_authentication.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthTemporaryLockUserResponse extends AuthUserLoginResponse {
  private String message;

  public static AuthTemporaryLockUserResponse from(String message) {
    AuthTemporaryLockUserResponse response = new AuthTemporaryLockUserResponse();
    response.setMessage(message);
    return response;
  }
}
