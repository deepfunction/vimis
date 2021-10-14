package ru.akrtkv.egisz.vimis.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.akrtkv.egisz.vimis.dto.vimis.callback.CallbackResponse;
import ru.akrtkv.egisz.vimis.dto.vimis.callback.SendResult;
import ru.akrtkv.egisz.vimis.service.CallbackService;
import ru.akrtkv.egisz.vimis.service.Log;

import static ru.akrtkv.egisz.vimis.utils.Constants.CALLBACK_NAMESPACE_URI;

@Endpoint
public class CallbackEndpoint {

    private final CallbackService callbackService;

    @Autowired
    public CallbackEndpoint(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @Log
    @PayloadRoot(namespace = CALLBACK_NAMESPACE_URI, localPart = "sendResult")
    @ResponsePayload
    public CallbackResponse sendResult(@RequestPayload SendResult sendResult) {
        return callbackService.sendResult(sendResult);
    }
}
