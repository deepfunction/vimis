package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import ru.akrtkv.egisz.vimis.dto.vimis.*;
import ru.akrtkv.egisz.vimis.exception.WebServiceException;
import ru.akrtkv.egisz.vimis.interceptor.OnkoClientInterceptor;
import ru.akrtkv.egisz.vimis.utils.ObjectCreator;

import javax.annotation.PostConstruct;

@Service
public class OnkoWebService extends WebServiceGatewaySupport {

    private final OnkoClientInterceptor interceptor;

    private final ObjectCreator objectCreator;

    @Value("${egisz.onko.soap.endpoint.uri}")
    private String soapEndpointUri;

    @Autowired
    public OnkoWebService(OnkoClientInterceptor interceptor, ObjectCreator objectCreator) {
        this.interceptor = interceptor;
        this.objectCreator = objectCreator;
    }

    public OnkoWebService(WebServiceMessageFactory messageFactory, OnkoClientInterceptor interceptor, ObjectCreator objectCreator) {
        super(messageFactory);
        this.interceptor = interceptor;
        this.objectCreator = objectCreator;
    }

    @PostConstruct
    private void configureService() {
        setDefaultUri(soapEndpointUri);
        Jaxb2Marshaller marshaller = objectCreator.createMarshaller();
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setInterceptors(new ClientInterceptor[]{interceptor});
    }

    @Log
    @CircuitBreaker(maxAttempts = 9, openTimeout = 300000, resetTimeout = 600000)
    public ReturnMsgId sendDocument(SendDocument request) {
        try {
            return (ReturnMsgId) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (WebServiceIOException e) {
            throw new WebServiceException(e);
        }
    }

    @Log
    @Recover
    public ReturnMsgId fallback(Exception e) {
        var returnMsgId = new ReturnMsgId();
        returnMsgId.setError(e.getMessage());
        return returnMsgId;
    }

    public ClinrecListResponse getClinrecList(GetClinrecList request) {
        try {
            return (ClinrecListResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (WebServiceIOException e) {
            throw new WebServiceException(e);
        }
    }

    public ClinrecInfoResponse getClinrecInfo(GetClinrecInfo request) {
        try {
            return (ClinrecInfoResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (WebServiceIOException e) {
            throw new WebServiceException(e);
        }
    }

    public ProcPMCListResponse getProcPMCList(GetProcPMCList request) {
        try {
            return (ProcPMCListResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (WebServiceIOException e) {
            throw new WebServiceException(e);
        }
    }

    public ProcPMCInfoResponse getProcPMCInfo(GetProcPMCInfo request) {
        try {
            return (ProcPMCInfoResponse) getWebServiceTemplate().marshalSendAndReceive(request);
        } catch (WebServiceIOException e) {
            throw new WebServiceException(e);
        }
    }
}
