package ru.akrtkv.egisz.vimis.utils;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import ru.akrtkv.egisz.vimis.dto.vimis.*;

@Component
public class ObjectCreator {

    public Jaxb2Marshaller createMarshaller() {
        var marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(
                SendDocument.class,
                ReturnMsgId.class,
                GetClinrecList.class,
                ClinrecListResponse.class,
                GetClinrecInfo.class,
                ClinrecInfoResponse.class,
                GetProcPMCList.class,
                ProcPMCListResponse.class,
                GetProcPMCInfo.class,
                ProcPMCInfoResponse.class
        );
        return marshaller;
    }
}
