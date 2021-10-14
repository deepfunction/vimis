package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "getProcPMCList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "summary"
})
public class ProcPMCListResponse {

    protected List<Summary> summary;

    public List<Summary> getSummary() {
        return summary;
    }

    public void setSummary(List<Summary> summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "ProcPMCListResponse{" +
                "summary=" + summary +
                '}';
    }
}
