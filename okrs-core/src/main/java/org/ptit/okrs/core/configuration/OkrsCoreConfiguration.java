package org.ptit.okrs.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"org.ptit.okrs.core.repository"})
@EnableJpaAuditing
public class OkrsCoreConfiguration {
}
