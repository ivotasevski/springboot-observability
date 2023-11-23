package com.ivotasevski.observability.service_a;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
class Config {
    // IMPORTANT! To instrument RestTemplate you must inject the RestTemplateBuilder
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

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