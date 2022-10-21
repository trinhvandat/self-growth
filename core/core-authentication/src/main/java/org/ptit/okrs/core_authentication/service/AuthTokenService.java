package org.ptit.okrs.core_authentication.service;

public interface AuthTokenService {
  String generateAccessToken(String userId, String email, String username);
  String getSubjectFromAccessToken(String accessToken);
  boolean validateAccessToken(String accessToken, String userId);
}
