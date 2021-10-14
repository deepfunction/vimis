package ru.akrtkv.egisz.vimis.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(RestClientLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        var response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("==========================request begin===========================");
            LOG.debug("URI         : {}", request.getURI());
            LOG.debug("Method      : {}", request.getMethod());
            LOG.debug("Headers     : {}", request.getHeaders());
            LOG.debug("Request body: {}", new String(body, StandardCharsets.UTF_8));
            LOG.debug("==========================request end=============================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("==========================response begin==========================");
            LOG.debug("Status code  : {}", response.getStatusCode());
            LOG.debug("Status text  : {}", response.getStatusText());
            LOG.debug("Headers      : {}", response.getHeaders());
            LOG.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
            LOG.debug("==========================response end============================");
        }
    }
}
