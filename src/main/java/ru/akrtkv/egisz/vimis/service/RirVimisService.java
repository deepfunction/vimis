package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.rir.Document;
import ru.akrtkv.egisz.vimis.dto.rir.ErrorMessage;
import ru.akrtkv.egisz.vimis.utils.Converter;

import java.time.LocalDate;
import java.util.List;

@Service
public class RirVimisService {

    private final DocumentService documentService;

    private final Converter converter;

    private final DocumentSendService documentSendService;

    private final InputDataProducerService inputDataProducerService;

    @Autowired
    public RirVimisService(DocumentService documentService, Converter converter, DocumentSendService documentSendService, InputDataProducerService inputDataProducerService) {
        this.documentService = documentService;
        this.converter = converter;
        this.documentSendService = documentSendService;
        this.inputDataProducerService = inputDataProducerService;
    }

    public Document sendDocument(Document document) {
        var documentModel = documentService.saveDocument(converter.convertToDocModel(document));
        inputDataProducerService.produceMisRequest(converter.convertToMessage(document));
        if (documentModel.getId() != null) {
            document.setRirId(documentModel.getId());
            documentSendService.addDocIdToQueue(documentModel.getId());
        }
        return document;
    }

    public List<ErrorMessage> getErrorMessages(LocalDate dateStart, LocalDate dateEnd) {
        return documentService.getErrorMessages(dateStart, dateEnd);
    }
}
