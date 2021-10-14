package ru.akrtkv.egisz.vimis.utils;

import org.springframework.stereotype.Component;
import ru.akrtkv.egisz.vimis.dto.rir.Message;
import ru.akrtkv.egisz.vimis.dto.vimis.Clinrec;
import ru.akrtkv.egisz.vimis.dto.vimis.Pmc;
import ru.akrtkv.egisz.vimis.dto.vimis.SendDocument;
import ru.akrtkv.egisz.vimis.exception.ConverterException;
import ru.akrtkv.egisz.vimis.model.Document;
import ru.akrtkv.egisz.vimis.model.SendDocumentResult;
import ru.akrtkv.egisz.vimis.model.Signature;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.SOAPBody;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;

@Component
public class Converter {

    public SendDocument convertToSendDocRequest(Document document) {
        var sendDocument = new SendDocument();
        sendDocument.setVmcl(document.getVimisType());
        sendDocument.setDocType(document.getDocType());
        sendDocument.setDocTypeVersion(document.getDocTypeVersion());
        sendDocument.setInterimMsg(0);
        if (document.getSignature() != null) {
            sendDocument.setSignature(new ru.akrtkv.egisz.vimis.dto.vimis.Signature());
            sendDocument.getSignature().setData(document.getSignature().getData());
            sendDocument.getSignature().setChecksum(document.getSignature().getChecksum());
        }
        sendDocument.setDocument(document.getEncodedDocument());
        return sendDocument;
    }

    public Document convertToDocModel(ru.akrtkv.egisz.vimis.dto.rir.Document document) {
        var documentModel = new Document();
        documentModel.setMisId(document.getMisId());
        documentModel.setMoOid(document.getMoOid());
        documentModel.setVimisType(document.getVimisType());
        documentModel.setDocType(document.getDocType());
        documentModel.setDocTypeVersion(document.getDocTypeVersion());
        documentModel.setEncodedDocument(document.getEncodedDocument());
        if (document.getSignature() != null) {
            documentModel.setSignature(new Signature());
            documentModel.getSignature().setData(document.getSignature().getData());
            documentModel.getSignature().setChecksum(document.getSignature().getChecksum());
        }
        documentModel.setSendDocumentResult(new SendDocumentResult());
        return documentModel;
    }

    public Clinrec convertToClinrec(String document) {
        try {
            return serialize(decodeBase64(document), Clinrec.class);
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    public Pmc convertToPmc(String document) {
        try {
            return serialize(decodeBase64(document), Pmc.class);
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    public <T> T serialize(String xmlString, Class<T> classForSerialize) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classForSerialize);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            return classForSerialize.cast(jaxbUnmarshaller.unmarshal(reader));
        } catch (JAXBException e) {
            throw new ConverterException(e);
        }
    }

    public String convertSoapBodyToString(SOAPBody element) {
        try {
            var source = new DOMSource(element);
            var stringResult = new StringWriter();
            var factory = TransformerFactory.newInstance();
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
            factory.newTransformer().transform(source, new StreamResult(stringResult));
            return stringResult.toString();
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    public String decodeBase64(String messageBase64) {
        return new String(Base64.getDecoder().decode(messageBase64.getBytes()), StandardCharsets.UTF_8);
    }

    public Message convertToMessage(ru.akrtkv.egisz.vimis.dto.rir.Document document) {
        var message = new Message();
        message.setRequestType("misRequest");
        message.setMisId(document.getMisId());
        message.setMoOid(document.getMoOid());
        message.setVimisType(document.getVimisType());
        message.setDocType(document.getDocType());
        message.setDateTime(LocalDateTime.now());
        return message;
    }

    public Message convertToMessage(Document document, String requestType) {
        var message = new Message();
        message.setRequestType(requestType);
        message.setMisId(document.getMisId());
        message.setMessageId(document.getSendDocumentResult().getMessageId());
        message.setMoOid(document.getMoOid());
        message.setVimisType(document.getVimisType());
        message.setDocType(document.getDocType());
        message.setDateTime(LocalDateTime.now());
        return message;
    }
}
