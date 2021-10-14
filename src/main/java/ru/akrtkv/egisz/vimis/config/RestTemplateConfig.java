package ru.akrtkv.egisz.vimis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.akrtkv.egisz.vimis.interceptor.RestClientLoggingInterceptor;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    private final RestClientLoggingInterceptor interceptor;

    @Autowired
    public RestTemplateConfig(RestClientLoggingInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        var factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        var restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(interceptor));
        return restTemplate;
    }
}
