package org.ptit.okrs.core_authentication.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthUserLoginResponse {
  private String accessToken;

  //TODO: pending
  private String refreshToken;
  private String tokenType = "Bearer";
  private long accessTokenLifeTime;
  private long refreshTokenLifeTime;
}
