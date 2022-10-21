package org.ptit.okrs.core.configuration;

import org.ptit.okrs.core.repository.DailyPlanRepository;
import org.ptit.okrs.core.service.DailyPlanService;
import org.ptit.okrs.core.service.impl.DailyPlanServiceImpl;
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
}
