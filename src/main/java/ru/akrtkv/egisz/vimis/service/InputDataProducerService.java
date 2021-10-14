package ru.akrtkv.egisz.vimis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.rir.Message;
import ru.akrtkv.egisz.vimis.dto.vimis.ReturnMsgId;
import ru.akrtkv.egisz.vimis.utils.Converter;

import java.util.UUID;

@Service
public class InputDataProducerService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    private final DocumentService documentService;

    private final Converter converter;

    @Value("${topic.request}")
    String topicRequest;

    @Value("${topic.sent}")
    String topicSent;

    @Value("${topic.callback}")
    String topicCallback;

    @Autowired
    public InputDataProducerService(KafkaTemplate<String, Message> kafkaTemplate, DocumentService documentService, Converter converter) {
        this.kafkaTemplate = kafkaTemplate;
        this.documentService = documentService;
        this.converter = converter;
    }

    public void produceMisRequest(Message message) {
        kafkaTemplate.send(topicRequest, UUID.randomUUID().toString(), message);
    }

    public void produceSent(ReturnMsgId returnMsgId) {
        var document = documentService.getDocByMessageId(returnMsgId.getMsgId());
        if (document != null) {
            kafkaTemplate.send(topicSent, UUID.randomUUID().toString(), converter.convertToMessage(document, "sent"));
        }
    }

    public void produceCallback(Message message) {
        kafkaTemplate.send(topicCallback, UUID.randomUUID().toString(), message);
    }
}
