package ru.akrtkv.egisz.vimis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;
import ru.akrtkv.egisz.vimis.dto.rir.Document;

@Service
public class TestService {

    public Document getDocument() throws JsonProcessingException {
        var jsonMapper = JsonMapper.builder().findAndAddModules().build();
        return jsonMapper.readValue(
                getDocumentJson(),
                Document.class
        );
    }

    public String getDocumentJson() {
        return """
                {
                  "misId": "1",
                  "moOid": "1.2.643.5.1.13.13.12.2.48.4521",
                  "vimisType": "3",
                  "docType": "1",
                  "docTypeVersion": "2",
                  "encodedDocument": "dGVzdA=="
                }
                """;
    }
}
