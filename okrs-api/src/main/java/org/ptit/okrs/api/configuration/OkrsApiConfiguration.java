package org.ptit.okrs.api.configuration;

import org.ptit.okrs.core.configuration.EnableOkrsCore;
import org.ptit.okrs.core_api_exception.configuration.EnableCoreApiException;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCoreApiException
@EnableOkrsCore
public class OkrsApiConfiguration {
}
