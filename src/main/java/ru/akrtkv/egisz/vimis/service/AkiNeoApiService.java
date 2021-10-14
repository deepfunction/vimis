package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.akrtkv.egisz.vimis.dto.vimis.KasRequest;
import ru.akrtkv.egisz.vimis.dto.vimis.KasResponse;
import ru.akrtkv.egisz.vimis.exception.AkiNeoApiServiceException;

@Service
public class AkiNeoApiService {

    private final RestTemplate restTemplate;

    private final RetryTemplate retryTemplate;

    @Value("${egisz.akineo.genIdKas.rest.endpoint.uri}")
    String akiNeoKasApiServiceUrl;

    @Autowired
    public AkiNeoApiService(RestTemplate restTemplate, RetryTemplate retryTemplate) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
    }

    public KasResponse getKasId(KasRequest kasRequest) {
        try {
            return retryTemplate.execute((RetryCallback<KasResponse, Exception>) context -> {
                var request = RequestEntity.post(akiNeoKasApiServiceUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .body(kasRequest);
                var responseEntity = restTemplate.exchange(request, KasResponse.class);
                return responseEntity.getBody();
            });
        } catch (Exception e) {
            throw new AkiNeoApiServiceException(e);
        }
    }
}
