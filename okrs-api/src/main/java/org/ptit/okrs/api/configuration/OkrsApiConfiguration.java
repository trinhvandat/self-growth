package org.ptit.okrs.api.configuration;

import org.ptit.okrs.core.configuration.EnableOkrsCore;
import org.ptit.okrs.core_api_exception.configuration.EnableCoreApiException;
import org.ptit.okrs.core_swagger.EnableCoreSwagger;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCoreApiException
@EnableCoreSwagger
@EnableOkrsCore
public class OkrsApiConfiguration {
}
