package org.ptit.okrs.core_authentication.configuration;

import java.util.concurrent.TimeUnit;
import org.ptit.okrs.core_authentication.facade.AuthFacadeService;
import org.ptit.okrs.core_authentication.facade.AuthFacadeServiceImpl;
import org.ptit.okrs.core_authentication.repository.AuthAccountRepository;
import org.ptit.okrs.core_authentication.repository.AuthUserRepository;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.ptit.okrs.core_authentication.service.OtpService;
import org.ptit.okrs.core_authentication.service.TokenRedisService;
import org.ptit.okrs.core_authentication.service.impl.AuthAccountServiceImpl;
import org.ptit.okrs.core_authentication.service.impl.AuthTokenServiceImpl;
import org.ptit.okrs.core_authentication.service.impl.AuthUserServiceImpl;
import org.ptit.okrs.core_authentication.service.impl.OtpServiceImpl;
import org.ptit.okrs.core_authentication.service.impl.TokenRedisServiceImpl;
import org.ptit.okrs.core_email.configuration.EnableCoreEmail;
import org.ptit.okrs.core_email.service.EmailService;
import org.ptit.okrs.core_redis.config.EnableCoreRedis;
import org.ptit.okrs.core_swagger.EnableCoreSwagger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;

@ComponentScan(basePackages = {"org.ptit.okrs.core_authentication.repository"})
@Configuration
@EnableJpaRepositories(
    basePackages = {"org.ptit.okrs.core_authentication.repository"},
    transactionManagerRef = "jpaAuthTransactionManager")
@EntityScan(basePackages = {"org.ptit.okrs.core_authentication.entity"})
@EnableCoreRedis
@EnableCoreSwagger
@EnableCoreEmail
public class CoreAuthenticationConfiguration {

  @Value("${application.authentication.access_token.jwt_secret:xxx}")
  private String accessTokenJwtSecret;

  @Value("${application.authentication.access_token.life_time}")
  private Long accessTokenLifeTime;

  @Value("${application.authentication.refresh_token.jwt_secret:xxx}")
  private String refreshTokenJwtSecret;

  @Value("${application.authentication.refresh_token.life_time}")
  private Long refreshTokenLifeTime;

  @Value("${application.authentication.redis.otp_time_out:3}")
  private Integer redisOtpTimeOut;

  @Bean
  public AuthAccountService authAccountService(AuthAccountRepository repository) {
    return new AuthAccountServiceImpl(repository);
  }

  @Bean
  public AuthFacadeService authFacadeService(
      AuthAccountService authAccountService,
      AuthUserService authUserService,
      AuthTokenService authTokenService,
      OtpService otpService,
      TokenRedisService tokenRedisService,
      EmailService emailService) {
    return new AuthFacadeServiceImpl(
        authAccountService,
        authUserService,
        authTokenService,
        otpService,
        tokenRedisService,
        emailService,
        accessTokenLifeTime,
        refreshTokenLifeTime);
  }

  @Bean
  public AuthUserService authUserService(AuthUserRepository repository) {
    return new AuthUserServiceImpl(repository);
  }

  @Bean
  public AuthTokenService authTokenService() {
    return new AuthTokenServiceImpl(
        accessTokenJwtSecret, accessTokenLifeTime, refreshTokenJwtSecret, refreshTokenLifeTime);
  }

  @Bean
  public OtpService otpService(RedisTemplate<String, Object> redisTemplate) {
    return new OtpServiceImpl(redisTemplate, redisOtpTimeOut, TimeUnit.MINUTES);
  }

  @Bean
  public TokenRedisService tokenRedisService(RedisTemplate<String, String> redisTemplate) {
    return new TokenRedisServiceImpl(redisTemplate);
  }
}
