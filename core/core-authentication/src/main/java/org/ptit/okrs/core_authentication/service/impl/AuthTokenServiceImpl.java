package org.ptit.okrs.core_authentication.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class AuthTokenServiceImpl implements AuthTokenService {

  private final String accessTokenJwtSecret;
  private final Long accessTokenLifeTime;

  public AuthTokenServiceImpl(String accessTokenJwtSecret, Long accessTokenLifeTime) {
    this.accessTokenJwtSecret = accessTokenJwtSecret;
    this.accessTokenLifeTime = accessTokenLifeTime;
  }

  @Override
  public String generateAccessToken(String userId, String email, String username) {
    log.info("(generateAccessToken)userId: {}, email: {}, username: {}", userId, email, username);
    var claims = new HashMap<String, Object>();
    claims.put("email", email);
    claims.put("username", username);
    return generateToken(userId, claims, accessTokenLifeTime, accessTokenJwtSecret);
  }

  @Override
  public String getSubjectFromAccessToken(String accessToken) {
    log.debug("(getSubjectFromAccessToken)accessToken: {}", accessToken);
    return getClaim(accessToken, Claims::getSubject, accessTokenJwtSecret);
  }

  @Override
  public boolean validateAccessToken(String accessToken, String userId) {
    log.info("(validateAccessToken)accessToken: {}, userId: {}", accessToken, userId);
    return getSubjectFromAccessToken(accessToken).equals(userId) && !isExpiredToken(accessToken, accessTokenJwtSecret);
  }

  private String generateToken(String subject, Map<String, Object> claims, long tokenLifeTime, String jwtSecret) {
    return Jwts.builder()
        .setSubject(subject)
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + tokenLifeTime))
        .signWith(SignatureAlgorithm.HS256, jwtSecret)
        .compact();
  }

  private boolean isExpiredToken(String token, String secretKey) {
    return getClaim(token, Claims::getExpiration, secretKey).before(new Date());
  }

  private <T> T getClaim(String token, Function<Claims, T> claimsResolve, String secretKey) {
    return claimsResolve.apply(getClaims(token, secretKey));
  }

  private Claims getClaims(String token, String secretKey) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }
}
