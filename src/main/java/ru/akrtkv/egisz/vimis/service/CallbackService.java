package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.vimis.callback.CallbackResponse;
import ru.akrtkv.egisz.vimis.dto.vimis.callback.SendResult;
import ru.akrtkv.egisz.vimis.utils.Converter;

@Service
public class CallbackService {

    private final MisApiService misApiService;

    private final DocumentService documentService;

    private final MisMoService misMoService;

    private final InputDataProducerService inputDataProducerService;

    private final Converter converter;

    @Autowired
    public CallbackService(MisApiService misApiService, DocumentService documentService, MisMoService misMoService, InputDataProducerService inputDataProducerService, Converter converter) {
        this.misApiService = misApiService;
        this.documentService = documentService;
        this.misMoService = misMoService;
        this.inputDataProducerService = inputDataProducerService;
        this.converter = converter;
    }

    public CallbackResponse sendResult(SendResult sendResult) {
        var vimisStatus = sendResult.getStatus() == 0 ? "SUCCESS" : "ERROR";
        var document = documentService.saveSendResult(
                sendResult.getMsgId(),
                vimisStatus,
                sendResult.getDescription()
        );
        if (document != null) {
            inputDataProducerService.produceCallback(converter.convertToMessage(document, "callback"));
        }
        var doc = documentService.getDocByMessageId(sendResult.getMsgId());
        if (doc != null) {
            var misMo = misMoService.getMisMoByOrgOid(doc.getMoOid());
            if (misMo != null && misMoService.checkMoState(misMo)) {
                var misResponse = misApiService.sendDocumentResult(
                        misMo,
                        doc.getMisId(),
                        vimisStatus,
                        sendResult.getDescription()
                );
                documentService.saveSendResultMisDeliveryStatus(misResponse, sendResult.getMsgId());
            }
        }
        return new CallbackResponse(0);
    }
}
