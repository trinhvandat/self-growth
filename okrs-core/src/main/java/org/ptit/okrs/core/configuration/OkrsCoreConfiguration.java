package org.ptit.okrs.core.configuration;

import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.repository.KeyResultRepository;
import org.ptit.okrs.core.repository.ObjectiveRepository;
import org.ptit.okrs.core.repository.UserRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.KeyResultService;
import org.ptit.okrs.core.service.ObjectiveService;
import org.ptit.okrs.core.service.UserService;
import org.ptit.okrs.core.service.impl.DailyPlanServiceImpl;
import org.ptit.okrs.core.service.impl.KeyResultServiceImpl;
import org.ptit.okrs.core.service.impl.ObjectiveServiceImpl;
import org.ptit.okrs.core.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"org.ptit.okrs.core.repository"})
@ComponentScan(basePackages = {"org.ptit.okrs.core.repository"})
@EnableJpaAuditing
public class OkrsCoreConfiguration {

  @Bean
  public DailyPlanService dailyPlanService(DailyPlanRepository repository) {
    return new DailyPlanServiceImpl(repository);
  }

  @Bean
  public ObjectiveService objectiveService(ObjectiveRepository repository) {
    return new ObjectiveServiceImpl(repository);
  }

  @Bean
  public KeyResultService keyResultService(KeyResultRepository repository) {
    return new KeyResultServiceImpl(repository);
  }

  @Bean
  public UserService userService(UserRepository repository) {
    return new UserServiceImpl(repository);
  }
}
