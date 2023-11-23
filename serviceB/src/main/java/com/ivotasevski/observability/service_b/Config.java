package com.ivotasevski.observability.service_b;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration(proxyBeanMethods = false)
class Config {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false);
        filter.setIncludeHeaders(false);
        filter.setBeforeMessagePrefix("REQUEST START: ");
        filter.setAfterMessagePrefix("REQUEST END: ");
        return filter;
    }
}