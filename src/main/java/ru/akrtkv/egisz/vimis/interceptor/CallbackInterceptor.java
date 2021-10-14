package ru.akrtkv.egisz.vimis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import ru.akrtkv.egisz.vimis.exception.InterceptorException;
import ru.akrtkv.egisz.vimis.service.SoapMessageService;

@Component
public class CallbackInterceptor implements EndpointInterceptor {

    private final SoapMessageService soapMessageService;

    @Autowired
    public CallbackInterceptor(SoapMessageService soapMessageService) {
        this.soapMessageService = soapMessageService;
    }

    @Override
    public boolean handleRequest(MessageContext messageContext, Object endpoint) {
        try {
            var saajSoapMessage = (SaajSoapMessage) messageContext.getRequest();
            var soapMessage = saajSoapMessage.getSaajMessage();
            soapMessageService.formResponseMessage(soapMessage);
            messageContext.setResponse(saajSoapMessage);
            soapMessageService.logResponse(messageContext);
            return true;
        } catch (Exception e) {
            throw new InterceptorException(e);
        }
    }

    @Override
    public boolean handleResponse(MessageContext messageContext, Object endpoint) {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext, Object endpoint) {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) {
    }
}
