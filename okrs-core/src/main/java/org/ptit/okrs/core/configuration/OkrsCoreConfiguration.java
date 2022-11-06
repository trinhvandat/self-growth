package org.ptit.okrs.core.configuration;

import org.ptit.okrs.core.repository.*;
import org.ptit.okrs.core.service.*;
import org.ptit.okrs.core.service.impl.*;
import org.ptit.orks.core_audit.AuditorAwareImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"org.ptit.okrs.core.repository"})
@ComponentScan(basePackages = {"org.ptit.okrs.core.repository"})
@EnableJpaAuditing
@EntityScan(basePackages = "org.ptit.okrs.core.entity")
public class OkrsCoreConfiguration {

  @Bean
  public AuditorAware<String> auditorAware() {
    return new AuditorAwareImpl();
  }

  @Bean
  public DailyPlanService dailyPlanService(DailyPlanRepository repository, UserService userService) {
    return new DailyPlanServiceImpl(repository, userService);
  }

  @Bean
  public ObjectiveService objectiveService(
      ObjectiveRepository repository, KeyResultService keyResultService) {
    return new ObjectiveServiceImpl(repository, keyResultService);
  }

  @Bean
  public KeyResultService keyResultService(
      KeyResultRepository repository) {
    return new KeyResultServiceImpl(repository);
  }

  @Bean
  public UserService userService(UserRepository repository) {
    return new UserServiceImpl(repository);
  }

  @Bean
  public AccountService accountService(AccountRepository repository) {
    return new AccountServiceImpl(repository);
  }

  @Bean
  public NotificationService notificationService(NotificationRepository repository) {
    return new NotificationServiceImpl(repository);
  }
}
