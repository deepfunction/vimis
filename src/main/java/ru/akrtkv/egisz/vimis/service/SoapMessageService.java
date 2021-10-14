package ru.akrtkv.egisz.vimis.service;

import org.apache.xml.security.c14n.Canonicalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import ru.akrtkv.egisz.vimis.exception.SoapMessageServiceException;
import ru.akrtkv.egisz.vimis.utils.Converter;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.Base64;
import java.util.Locale;
import java.util.UUID;

import static ru.akrtkv.egisz.vimis.utils.Constants.*;

@Service
public class SoapMessageService {

    private static final Logger LOG = LoggerFactory.getLogger(SoapMessageService.class);

    private final SoapCertService certService;

    private final MessageSource messageSource;

    private final Converter converter;

    @Value("${client.id}")
    private String clientId;

    @Value("${callback.uri}")
    private String callbackUri;

    @Value("${egisz.akineo.soap.endpoint.uri}")
    private String akiNeoSoapEndpointUri;

    @Value("${egisz.ssz.soap.endpoint.uri}")
    private String sszSoapEndpointUri;

    @Value("${egisz.onko.soap.endpoint.uri}")
    private String onkoSoapEndpointUri;

    @Autowired
    public SoapMessageService(SoapCertService certService, MessageSource messageSource, Converter converter) {
        this.certService = certService;
        this.messageSource = messageSource;
        this.converter = converter;
    }

    public void formAkiNeoRequestMessage(SOAPMessage message) {
        formRequestMessage(message, akiNeoSoapEndpointUri);
    }

    public void formSszRequestMessage(SOAPMessage message) {
        formRequestMessage(message, sszSoapEndpointUri);
    }

    public void formOnkoRequestMessage(SOAPMessage message) {
        formRequestMessage(message, onkoSoapEndpointUri);
    }

    private void formRequestMessage(SOAPMessage message, String soapEndpointUri) {
        try {
            var envelope = message.getSOAPPart().getEnvelope();
            message.getSOAPHeader().removeContents();
            envelope.setAttribute("xmlns:wsa", WWW_W3_ORG_2005_08_ADDRESSING);
            envelope.setAttribute("xmlns:wsse", OASIS_200401_WSS_WSSECURITY_SECEXT);
            envelope.setAttribute("xmlns:wsu", OASIS_200401_WSS_WSSECURITY_UTILITY);
            envelope.setAttribute("xmlns:ds", WWW_W3_ORG_2000_09_XMLDSIG);
            setClientId(message.getSOAPHeader());
            var action = "";
            var soapBodyString = converter.convertSoapBodyToString(message.getSOAPBody());
            if (soapBodyString.contains("sendDocument")) {
                action = "sendDocument";
            } else if (soapBodyString.contains("getClinrecList")) {
                action = "clinrecList";
            } else if (soapBodyString.contains("getClinrecInfo")) {
                action = "clinrecInfo";
            } else if (soapBodyString.contains("getProcPMCList")) {
                action = "procPMCList";
            } else {
                action = "procPMCInfo";
            }
            setRequestAddressing(message.getSOAPHeader(), action, soapEndpointUri);
            setSecurity(message);
            message.saveChanges();
        } catch (Exception e) {
            throw new SoapMessageServiceException(e);
        }
    }

    public void formResponseMessage(SOAPMessage message) {
        try {
            var envelope = message.getSOAPPart().getEnvelope();
            var body = envelope.getBody();
            message.getSOAPHeader().removeContents();
            envelope.setAttribute("xmlns:wsa", WWW_W3_ORG_2005_08_ADDRESSING);
            envelope.setAttribute("xmlns:wsse", OASIS_200401_WSS_WSSECURITY_SECEXT);
            envelope.setAttribute("xmlns:wsu", OASIS_200401_WSS_WSSECURITY_UTILITY);
            envelope.setAttribute("xmlns:ds", WWW_W3_ORG_2000_09_XMLDSIG);
            var messageIdNode = body.getElementsByTagName("msg_id");
            var messageId = messageIdNode.item(0).getChildNodes().item(0).getNodeValue();
            setCallbackResponseAddressing(message.getSOAPHeader(), messageId);
            setSecurity(message);
            message.saveChanges();
        } catch (Exception e) {
            throw new SoapMessageServiceException(e);
        }
    }

    public void logRequest(MessageContext messageContext) throws SOAPException, IOException {
        var out = new ByteArrayOutputStream();
        var saajSoapMessage = (SaajSoapMessage) messageContext.getRequest();
        var soapMessage = saajSoapMessage.getSaajMessage();
        soapMessage.writeTo(out);
        var soapMessageString = out.toString(StandardCharsets.UTF_8);
        out.close();
        LOG.info(soapMessageString);
    }

    public void logResponse(MessageContext messageContext) throws SOAPException, IOException {
        var out = new ByteArrayOutputStream();
        var saajSoapMessage = (SaajSoapMessage) messageContext.getResponse();
        var soapMessage = saajSoapMessage.getSaajMessage();
        soapMessage.writeTo(out);
        var soapMessageString = out.toString(StandardCharsets.UTF_8);
        out.close();
        LOG.info(soapMessageString);
    }

    private void setClientId(SOAPHeader soapHeader) {
        try {
            var transportHeaderElement = soapHeader.addChildElement(new QName(EGISZ_NAMESPACE, "transportHeader", EGISZ_PREFIX));
            var authInfoElement = soapHeader.addChildElement(new QName(EGISZ_NAMESPACE, "authInfo", EGISZ_PREFIX));
            var clientEntityIdElement = soapHeader.addChildElement(new QName(EGISZ_NAMESPACE, "clientEntityId", EGISZ_PREFIX));
            clientEntityIdElement.setTextContent(clientId);
            authInfoElement.addChildElement(clientEntityIdElement);
            transportHeaderElement.addChildElement(authInfoElement);
        } catch (Exception e) {
            throw new SoapMessageServiceException(e);
        }
    }

    private void setRequestAddressing(SOAPHeader soapHeader, String action, String uri) {
        try {
            var messageIdElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, MESSAGE_ID, "wsa"));
            messageIdElement.setTextContent(UUID.randomUUID().toString());
            var toElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "To", "wsa"));
            toElement.setTextContent(uri);
            var replyToElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "ReplyTo", "wsa"));
            var addressReplyTo = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "Address", "wsa"));
            addressReplyTo.setTextContent(callbackUri);
            replyToElement.addChildElement(addressReplyTo);
            var faultToElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "FaultTo", "wsa"));
            var addressFaultTo = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "Address", "wsa"));
            addressFaultTo.setTextContent(callbackUri);
            faultToElement.addChildElement(addressFaultTo);
            var actionElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, ACTION, "wsa"));
            actionElement.setTextContent(action);
        } catch (Exception e) {
            throw new SoapMessageServiceException(e);
        }
    }

    private void setCallbackResponseAddressing(SOAPHeader soapHeader, String messageId) {
        try {
            var messageIdElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, MESSAGE_ID, "wsa"));
            messageIdElement.setTextContent(UUID.randomUUID().toString());
            var toElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "To", "wsa"));
            toElement.setTextContent("http://www.w3.org/2005/08/addressing/anonymous");
            var relatesToElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, "RelatesTo", "wsa"));
            relatesToElement.setTextContent(messageId);
            var actionElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2005_08_ADDRESSING, ACTION, "wsa"));
            actionElement.setTextContent("callbackResponse");
        } catch (Exception e) {
            throw new SoapMessageServiceException(e);
        }
    }

    private void setSecurity(SOAPMessage message) {
        try {
            var certificate = certService.getCertificate();

            if (certificate == null) {
                throw new SoapMessageServiceException(messageSource.getMessage("noCertificatesAvailable", null, Locale.getDefault()));
            }

            var bodyId = UUID.randomUUID().toString();
            message.getSOAPBody().setAttribute("wsu:Id", bodyId);

            var soapHeader = message.getSOAPHeader();

            var securityElement = soapHeader.addChildElement(new QName(OASIS_200401_WSS_WSSECURITY_SECEXT, "Security", "wsse"));

            var binarySecurityTokenElement = soapHeader.addChildElement(new QName(OASIS_200401_WSS_WSSECURITY_SECEXT, "BinarySecurityToken", "wsse"));
            binarySecurityTokenElement.addAttribute(message.getSOAPPart().getEnvelope().createName("EncodingType"), OASIS_200401_WSS_SOAP_MESSAGE_SECURITY);
            binarySecurityTokenElement.addAttribute(message.getSOAPPart().getEnvelope().createName("ValueType"), OASIS_200401_WSS_X509_TOKEN_PROFILE);
            binarySecurityTokenElement.addAttribute(message.getSOAPPart().getEnvelope().createName("Id", "wsu", OASIS_200401_WSS_WSSECURITY_UTILITY), "CertId");
            var certBytes = certificate.getEncoded();
            var base64Cert = new String(Base64.getEncoder().encode(certBytes), StandardCharsets.US_ASCII);
            binarySecurityTokenElement.setTextContent(base64Cert);
            securityElement.addChildElement(binarySecurityTokenElement);

            var signatureElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "Signature", "ds"));
            var signedInfoElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "SignedInfo", "ds"));
            var canonicalizationMethodElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "CanonicalizationMethod", "ds"));
            canonicalizationMethodElement.setAttribute(ALGORITHM, WWW_W3_ORG_2001_10_XML_EXC_C14N);
            signedInfoElement.addChildElement(canonicalizationMethodElement);
            var signatureMethodElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "SignatureMethod", "ds"));
            signatureMethodElement.setAttribute(ALGORITHM, WWW_W3_ORG_2001_04_XMLDSIG_GOST34102001);
            signedInfoElement.addChildElement(signatureMethodElement);

            var referenceElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "Reference", "ds"));
            referenceElement.setAttribute("URI", "#" + bodyId);

            var transformsElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "Transforms", "ds"));
            var transformElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "Transform", "ds"));
            transformElement.setAttribute(ALGORITHM, WWW_W3_ORG_2001_10_XML_EXC_C14N);
            transformsElement.addChildElement(transformElement);
            referenceElement.addChildElement(transformsElement);

            var digestValueElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "DigestValue", "ds"));
            var digestMethodElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "DigestMethod", "ds"));
            digestMethodElement.setAttribute(ALGORITHM, WWW_W3_ORG_2001_04_XMLDSIG_GOSTR3411);
            referenceElement.addChildElement(digestMethodElement);
            referenceElement.addChildElement(digestValueElement);

            signedInfoElement.addChildElement(referenceElement);
            signatureElement.addChildElement(signedInfoElement);

            var signatureValueElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "SignatureValue", "ds"));
            signatureElement.addChildElement(signatureValueElement);

            var keyInfoElement = soapHeader.addChildElement(new QName(WWW_W3_ORG_2000_09_XMLDSIG, "KeyInfo", "ds"));
            var securityTokenReferenceElement = soapHeader.addChildElement(new QName(OASIS_200401_WSS_WSSECURITY_SECEXT, "SecurityTokenReference", "wsse"));
            var referElement = soapHeader.addChildElement(new QName(OASIS_200401_WSS_WSSECURITY_SECEXT, "Reference", "wsse"));
            referElement.addAttribute(message.getSOAPPart().getEnvelope().createName("URI"), "#CertId");
            referElement.addAttribute(message.getSOAPPart().getEnvelope().createName("ValueType"), OASIS_200401_WSS_X509_TOKEN_PROFILE);
            securityTokenReferenceElement.addChildElement(referElement);
            keyInfoElement.addChildElement(securityTokenReferenceElement);
            signatureElement.addChildElement(keyInfoElement);
            securityElement.addChildElement(signatureElement);

            var canonicalizeSubtreeSoapBodyOutputStream = new ByteArrayOutputStream();
            Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS).canonicalizeSubtree(message.getSOAPBody(), canonicalizeSubtreeSoapBodyOutputStream);
            var canonicalizedBody = canonicalizeSubtreeSoapBodyOutputStream.toByteArray();

            var messageDigest = MessageDigest.getInstance(GOST_3411_2012_256, "ViPNet");
            var base64canonicalizeSubtree = new String(Base64.getEncoder().encode(messageDigest.digest(canonicalizedBody)), StandardCharsets.UTF_8);
            var nodes = message.getSOAPHeader().getElementsByTagNameNS(WWW_W3_ORG_2000_09_XMLDSIG, "DigestValue");
            nodes.item(0).setTextContent(base64canonicalizeSubtree);

            var canonicalizeSubtreeSignedElementOutputStream = new ByteArrayOutputStream();
            var signedElement = (SOAPElement) message.getSOAPHeader().getElementsByTagNameNS(WWW_W3_ORG_2000_09_XMLDSIG, "SignedInfo").item(0);
            Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS).canonicalizeSubtree(signedElement, canonicalizeSubtreeSignedElementOutputStream);
            var canonicalizedSignedElement = canonicalizeSubtreeSignedElementOutputStream.toByteArray();

            var signature = Signature.getInstance(GOST_3411_2012_256_WITH_GOST_3410_2012_256, "ViPNet");

            if (certService.getPrivateKey() == null) {
                throw new SoapMessageServiceException(messageSource.getMessage("noPrivateKey", null, Locale.getDefault()));
            }

            if (canonicalizedSignedElement.length == 0) {
                throw new SoapMessageServiceException(messageSource.getMessage("noSignatureData", null, Locale.getDefault()));
            }

            signature.initSign(certService.getPrivateKey());
            signature.update(canonicalizedSignedElement);
            var signBytes = signature.sign();

            var signatureBase64 = new String(Base64.getEncoder().encode(signBytes), StandardCharsets.UTF_8);
            var signedValueElement = (SOAPElement) message.getSOAPHeader().getElementsByTagNameNS(WWW_W3_ORG_2000_09_XMLDSIG, "SignatureValue").item(0);
            signedValueElement.setTextContent(signatureBase64);
        } catch (Exception e) {
            throw new SoapMessageServiceException(e);
        }
    }
}
