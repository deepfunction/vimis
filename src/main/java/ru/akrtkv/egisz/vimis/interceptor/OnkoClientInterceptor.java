package ru.akrtkv.egisz.vimis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import ru.akrtkv.egisz.vimis.exception.InterceptorException;
import ru.akrtkv.egisz.vimis.service.SoapMessageService;

@Component
public class OnkoClientInterceptor implements ClientInterceptor {

    private final SoapMessageService soapMessageService;

    @Autowired
    public OnkoClientInterceptor(SoapMessageService soapMessageService) {
        this.soapMessageService = soapMessageService;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext) {
        try {
            var saajSoapMessage = (SaajSoapMessage) messageContext.getRequest();
            var soapMessage = saajSoapMessage.getSaajMessage();
            soapMessageService.formOnkoRequestMessage(soapMessage);
            soapMessageService.logRequest(messageContext);
            return true;
        } catch (Exception e) {
            throw new InterceptorException(e);
        }
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) {
        try {
            soapMessageService.logResponse(messageContext);
            return true;
        } catch (Exception e) {
            throw new InterceptorException(e);
        }
    }

    @Override
    public boolean handleFault(MessageContext messageContext) {
        try {
            soapMessageService.logResponse(messageContext);
            return true;
        } catch (Exception e) {
            throw new InterceptorException(e);
        }
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) {
    }
}
