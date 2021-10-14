package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.akrtkv.egisz.vimis.dto.mis.MisResponse;
import ru.akrtkv.egisz.vimis.dto.mis.Status;
import ru.akrtkv.egisz.vimis.dto.rir.SendResult;
import ru.akrtkv.egisz.vimis.model.MisMo;

@Service
public class MisApiService {

    private final RestTemplate restTemplate;

    private final RetryTemplate retryTemplate;

    private final MisMoService misMoService;

    @Value("${mis.api.result.paths}")
    String misApiResultPaths;

    @Autowired
    public MisApiService(RestTemplate restTemplate, RetryTemplate retryTemplate, MisMoService misMoService) {
        this.restTemplate = restTemplate;
        this.retryTemplate = retryTemplate;
        this.misMoService = misMoService;
    }

    @Log
    public MisResponse sendDocumentResult(MisMo misMo, String misId, String status, String message) {
        try {
            return retryTemplate.execute((RetryCallback<MisResponse, Exception>) context -> {
                var request = new HttpEntity<>(new SendResult(status, message));
                var uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(misMo.getUrl());
                uriComponentsBuilder.path(misApiResultPaths);
                uriComponentsBuilder.queryParam("misVimisDocId", misId);
                var responseEntity = restTemplate.exchange(uriComponentsBuilder.toUriString(), HttpMethod.POST, request, MisResponse.class);
                misMoService.setMisAvailable(misMo);
                return responseEntity.getBody();
            });
        } catch (Exception e) {
            misMoService.disableMis(misMo, e.getMessage());
            return new MisResponse(Status.ERROR, e.getMessage());
        }
    }
}
