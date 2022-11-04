package org.ptit.okrs.core_authentication.configuration;

import org.ptit.okrs.core_authentication.repository.AuthAccountRepository;
import org.ptit.okrs.core_authentication.repository.AuthUserRepository;
import org.ptit.okrs.core_authentication.service.AuthAccountService;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.ptit.okrs.core_authentication.service.impl.AuthAccountServiceImpl;
import org.ptit.okrs.core_authentication.service.impl.AuthTokenServiceImpl;
import org.ptit.okrs.core_authentication.service.impl.AuthUserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = {"org.ptit.okrs.core_authentication.repository"},
    transactionManagerRef = "jpaAuthTransactionManager"
)
@ComponentScan(basePackages = {"org.ptit.okrs.core_authentication.repository"})
public class CoreAuthenticationConfiguration {

  @Value("${application.authentication.access_token.jwt_secret:xxx}")
  private String accessTokenJwtSecret;

  @Value("${application.authentication.access_token.life_time}")
  private Long accessTokenLifeTime;

  @Value("${application.authentication.refresh_token.jwt_secret:xxx}")
  private String refreshTokenJwtSecret;

  @Value("${application.authentication.refresh_token.life_time}")
  private Long refreshTokenLifeTime;

  @Bean
  public AuthAccountService authAccountService(AuthAccountRepository repository) {
    return new AuthAccountServiceImpl(repository);
  }

  @Bean
  public AuthUserService authUserService(AuthUserRepository repository) {
    return new AuthUserServiceImpl(repository);
  }

  @Bean
  public AuthTokenService authTokenService() {
    return new AuthTokenServiceImpl(
        accessTokenJwtSecret,
        accessTokenLifeTime,
        refreshTokenJwtSecret,
        refreshTokenLifeTime
    );
  }
}
