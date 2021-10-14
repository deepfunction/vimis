package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.akrtkv.egisz.vimis.dto.mis.MisResponse;
import ru.akrtkv.egisz.vimis.dto.rir.ErrorMessage;
import ru.akrtkv.egisz.vimis.dto.vimis.ReturnMsgId;
import ru.akrtkv.egisz.vimis.exception.DocumentServiceException;
import ru.akrtkv.egisz.vimis.model.Document;
import ru.akrtkv.egisz.vimis.model.SendDocumentResult;
import ru.akrtkv.egisz.vimis.model.Signature;
import ru.akrtkv.egisz.vimis.repository.DocumentRepository;
import ru.akrtkv.egisz.vimis.repository.SendDocumentResultRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService {

    private final SendDocumentResultRepository sendDocumentResultRepository;

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(SendDocumentResultRepository sendDocumentResultRepository, DocumentRepository documentRepository) {
        this.sendDocumentResultRepository = sendDocumentResultRepository;
        this.documentRepository = documentRepository;
    }

    public Document getNotSentDocById(long docId) {
        try {
            return documentRepository.findByIdAndSendDocumentResult_StatusSyncIsNull(docId);
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    public List<Long> getNotSentDocsIds() {
        try {
            return documentRepository.getNotSentDocsIds();
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    public Document getDocByMessageId(String messageId) {
        try {
            return documentRepository.findBySendDocumentResult_MessageId(messageId);
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Document saveSendResult(String messageId, String status, String description) {
        try {
            var document = documentRepository.findBySendDocumentResult_MessageId(messageId);
            if (document != null) {
                document.getSendDocumentResult().setStatusAsync(status);
                document.getSendDocumentResult().setDescription(description);
                documentRepository.save(document);
            }
            return document;
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveVimisSendSyncStatus(ReturnMsgId returnMsgId, Long docId) {
        try {
            if (returnMsgId.getMsgId() != null && docId != null) {
                var sendDocumentResult = sendDocumentResultRepository.getByDocId(docId);
                if (sendDocumentResult != null) {
                    sendDocumentResult.setStatusSync("SUCCESS");
                    sendDocumentResult.setMessageId(returnMsgId.getMsgId());
                    sendDocumentResult.setDescription(returnMsgId.getError());
                    sendDocumentResultRepository.save(sendDocumentResult);
                }
            }
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Document saveDocument(Document document) {
        try {
            var doc = documentRepository.findByMisId(document.getMisId());
            if (doc == null) {
                return documentRepository.save(document);
            } else {
                doc.setCreationDate(LocalDateTime.now());
                doc.setMoOid(document.getMoOid());
                doc.setVimisType(document.getVimisType());
                doc.setDocType(document.getDocType());
                doc.setDocTypeVersion(document.getDocTypeVersion());
                doc.setEncodedDocument(document.getEncodedDocument());
                if (document.getSignature() != null) {
                    if (doc.getSignature() == null) {
                        doc.setSignature(new Signature());
                    }
                    doc.getSignature().setData(document.getSignature().getData());
                    doc.getSignature().setChecksum(document.getSignature().getChecksum());
                }
                return documentRepository.save(doc);
            }
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveSendResultMisDeliveryStatus(MisResponse misResponse, String messageId) {
        try {
            var sendDocumentResult = sendDocumentResultRepository.findByMessageId(messageId);
            if (sendDocumentResult != null) {
                sendDocumentResult.setMisResultDeliveryStatus(misResponse.getStatus().toString());
                sendDocumentResult.setMisResultDeliveryError(misResponse.getError());
                sendDocumentResultRepository.save(sendDocumentResult);
            }
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }

    public List<ErrorMessage> getErrorMessages(LocalDate dateStart, LocalDate dateEnd) {
        try {
            List<SendDocumentResult> registerDocumentResults = sendDocumentResultRepository.findAllByCreationDateBetween(dateStart.atStartOfDay(), dateEnd.atStartOfDay().plusDays(1));
            List<ErrorMessage> errorMessageList = new ArrayList<>();
            for (var result : registerDocumentResults) {
                var errorMessageDto = new ErrorMessage();
                errorMessageDto.setMessageId(result.getMessageId());
                errorMessageDto.setStatusSync(result.getStatusSync());
                errorMessageDto.setStatusAsync(result.getStatusAsync());
                errorMessageDto.setError(result.getDescription());
                errorMessageDto.setMisResultDeliveryStatus(result.getMisResultDeliveryStatus());
                errorMessageDto.setMisResultDeliveryError(result.getMisResultDeliveryError());
                errorMessageList.add(errorMessageDto);
            }
            return errorMessageList;
        } catch (DataAccessException e) {
            throw new DocumentServiceException(e);
        }
    }
}
