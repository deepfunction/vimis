package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clinrecInfoResponse", propOrder = {
        "document"
})
public class ClinrecInfoResponse {

    private String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return "ClinrecInfoResponse{" +
                "document='" + document + '\'' +
                '}';
    }
}
