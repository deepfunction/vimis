package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clinrecListResponse", propOrder = {
        "summary"
})
public class ClinrecListResponse {

    private List<Summary> summary;

    public List<Summary> getSummary() {
        if (summary == null) {
            summary = new ArrayList<>();
        }
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ClinrecListResponse{" +
                "summary=" + summary +
                '}';
    }
}
