package ru.akrtkv.egisz.vimis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.vimis.ReturnMsgId;
import ru.akrtkv.egisz.vimis.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

@Service
public class DocumentSendService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentSendService.class);

    private final Queue<Long> documentsIds;

    private final AkiNeoWebService akiNeoWebService;

    private final OnkoWebService onkoWebService;

    private final SszWebService sszWebService;

    private final DocumentService documentService;

    private final InputDataProducerService inputDataProducerService;

    private final Converter converter;

    @Autowired
    public DocumentSendService(AkiNeoWebService akiNeoWebService, OnkoWebService onkoWebService, SszWebService sszWebService, DocumentService documentService, InputDataProducerService inputDataProducerService, Converter converter) {
        this.akiNeoWebService = akiNeoWebService;
        this.onkoWebService = onkoWebService;
        this.sszWebService = sszWebService;
        this.documentService = documentService;
        this.inputDataProducerService = inputDataProducerService;
        this.converter = converter;
        this.documentsIds = new ConcurrentLinkedDeque<>();
    }

    public void addDocIdToQueue(long docId) {
        documentsIds.offer(docId);
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 60000)
    private void sendDocs() {
        var executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<Void>> completableFutures = new ArrayList<>();
        try {
            if (!documentsIds.isEmpty()) {
                for (int i = 0; i <= documentsIds.size(); i++) {
                    var docId = documentsIds.poll();
                    completableFutures.add(
                            CompletableFuture.supplyAsync(
                                    () -> sendDoc(docId),
                                    executorService
                            ).thenAccept(returnMsgId -> {
                                documentService.saveVimisSendSyncStatus(returnMsgId, docId);
                                if (returnMsgId.getMsgId() != null) {
                                    inputDataProducerService.produceSent(returnMsgId);
                                }
                            })
                    );
                }
                completableFutures.forEach(CompletableFuture::join);
                shutdownExecutorService(executorService);
            } else {
                var docsIds = documentService.getNotSentDocsIds();
                if (docsIds != null && !docsIds.isEmpty()) {
                    for (long docId : docsIds) {
                        documentsIds.offer(docId);
                    }
                }
            }
        } catch (Exception e) {
            shutdownExecutorService(executorService);
            LOG.error(e.getMessage(), e.getCause());
        }
    }

    private ReturnMsgId sendDoc(Long docId) {
        if (docId != null) {
            var document = documentService.getNotSentDocById(docId);
            if (document != null) {
                var sendDocRequest = converter.convertToSendDocRequest(document);
                if (sendDocRequest.getVmcl() == 3) {
                    return akiNeoWebService.sendDocument(sendDocRequest);
                } else if (sendDocRequest.getVmcl() == 4) {
                    return sszWebService.sendDocument(sendDocRequest);
                } else {
                    return onkoWebService.sendDocument(sendDocRequest);
                }
            } else {
                return new ReturnMsgId();
            }
        } else {
            return new ReturnMsgId();
        }
    }

    private void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    LOG.error("ExecutorService did not terminate");
                }
            }
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e.getCause());
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
