package org.ptit.okrs.api.config;

import org.ptit.okrs.core.configuration.OkrsCoreConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;

@TestConfiguration
@ContextConfiguration(classes = {OkrsCoreConfiguration.class})
public class ApiTestConfig {
}
