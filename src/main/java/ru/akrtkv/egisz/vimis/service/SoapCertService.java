package ru.akrtkv.egisz.vimis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.infotecs.crypto.ViPNetProvider;
import ru.akrtkv.egisz.vimis.exception.CertServiceException;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;

@Service
public class SoapCertService {

    private static final Logger LOG = LoggerFactory.getLogger(SoapCertService.class);

    private X509Certificate certificate;

    private PrivateKey privateKey;

    @Value("${cert.container.path}")
    private String containerPathString;

    @Value("${cert.container.password}")
    private String password;

    @PostConstruct
    private void initSecurity() {
        Security.addProvider(new ViPNetProvider());
        org.apache.xml.security.Init.init();
        loadCert(containerPathString, password);
    }

    private void loadCert(String containerPathString, String password) {
        try (var inputStream = Files.newInputStream(Paths.get(containerPathString))) {
            var keyStore = KeyStore.getInstance("ViPNetContainer", "ViPNet");
            keyStore.load(inputStream, password.toCharArray());
            String alias = keyStore.aliases().nextElement();
            LOG.info("Reading the certificate: {}", alias);
            certificate = (X509Certificate) keyStore.getCertificate(alias);
            privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        } catch (Exception e) {
            throw new CertServiceException(e);
        }
    }

    public X509Certificate getCertificate() {
        return this.certificate;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
