package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.vimis.*;
import ru.akrtkv.egisz.vimis.utils.Converter;

@Service
public class SszService {

    private final SszWebService webService;

    private final Converter converter;

    @Autowired
    public SszService(SszWebService webService, Converter converter) {
        this.webService = webService;
        this.converter = converter;
    }

    public ClinrecListResponse getClinrecList() {
        return webService.getClinrecList(new GetClinrecList());
    }

    public Clinrec getClinrecInfo(String id) {
        var clinrecInfoResponse = webService.getClinrecInfo(new GetClinrecInfo(id));
        return converter.convertToClinrec(clinrecInfoResponse.getDocument());
    }

    public ProcPMCListResponse getProcPMCList() {
        return webService.getProcPMCList(new GetProcPMCList());
    }

    public Pmc getProcPMCInfo(String id) {
        var procPMCInfoResponse = webService.getProcPMCInfo(new GetProcPMCInfo(id));
        return converter.convertToPmc(procPMCInfoResponse.getDocument());
    }
}
