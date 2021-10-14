package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.vimis.*;
import ru.akrtkv.egisz.vimis.utils.Converter;

@Service
public class AkiNeoService {

    private final AkiNeoWebService akiNeoWebService;

    private final AkiNeoApiService akiNeoApiService;

    private final Converter converter;

    @Autowired
    public AkiNeoService(AkiNeoWebService akiNeoWebService, AkiNeoApiService akiNeoApiService, Converter converter) {
        this.akiNeoWebService = akiNeoWebService;
        this.akiNeoApiService = akiNeoApiService;
        this.converter = converter;
    }

    public ClinrecListResponse getClinrecList() {
        return akiNeoWebService.getClinrecList(new GetClinrecList());
    }

    public Clinrec getClinrecInfo(String id) {
        var clinrecInfoResponse = akiNeoWebService.getClinrecInfo(new GetClinrecInfo(id));
        return converter.convertToClinrec(clinrecInfoResponse.getDocument());
    }

    public ProcPMCListResponse getProcPMCList() {
        return akiNeoWebService.getProcPMCList(new GetProcPMCList());
    }

    public Pmc getProcPMCInfo(String id) {
        var procPMCInfoResponse = akiNeoWebService.getProcPMCInfo(new GetProcPMCInfo(id));
        return converter.convertToPmc(procPMCInfoResponse.getDocument());
    }

    public KasResponse getKasId(KasRequest kasRequest) {
        return akiNeoApiService.getKasId(kasRequest);
    }
}
