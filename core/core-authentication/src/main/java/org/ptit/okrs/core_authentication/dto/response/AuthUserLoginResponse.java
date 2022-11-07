package org.ptit.okrs.core_authentication.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthUserLoginResponse {
  private String accessToken;
  private String refreshToken;
  private String tokenType = "Bearer";
  private long accessTokenLifeTime;
  private long refreshTokenLifeTime;

  public static AuthUserLoginResponse from(
      String accessToken,
      String refreshToken,
      long accessTokenLifeTime,
      long refreshTokenLifeTime) {
    AuthUserLoginResponse response = new AuthUserLoginResponse();
    response.setAccessToken(accessToken);
    response.setRefreshToken(refreshToken);
    response.setAccessTokenLifeTime(accessTokenLifeTime);
    response.setRefreshTokenLifeTime(refreshTokenLifeTime);
    return response;
  }
}
