package ru.akrtkv.egisz.vimis;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.akrtkv.egisz.vimis.repository.DocumentRepository;
import ru.akrtkv.egisz.vimis.service.InputDataProducerService;
import ru.akrtkv.egisz.vimis.utils.ApiPaths;
import ru.akrtkv.egisz.vimis.utils.Converter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class VimisApplicationTests {

    @MockBean
    DocumentRepository documentRepository;

    @MockBean
    InputDataProducerService inputDataProducerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestService testService;

    @Autowired
    Converter converter;

    @Test
    void sendDocumentTest() throws Exception {
        var document = testService.getDocument();
        var documentModel = converter.convertToDocModel(document);
        var message = converter.convertToMessage(document);

        Mockito.when(documentRepository.findByMisId(document.getMisId())).thenReturn(null);
        Mockito.when(documentRepository.save(documentModel)).thenReturn(documentModel);
        Mockito.doNothing().when(inputDataProducerService).produceMisRequest(message);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/" + ApiPaths.BASE_PATH + ApiPaths.SEND_DOC)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(testService.getDocumentJson())
        ).andExpect(status().isOk());
    }
}
