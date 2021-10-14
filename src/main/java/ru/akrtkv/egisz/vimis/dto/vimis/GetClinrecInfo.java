package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getClinrecInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id"
})
public class GetClinrecInfo {

    protected String id;

    public GetClinrecInfo() {
    }

    public GetClinrecInfo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "GetProcPMCInfo{" +
                "id='" + id + '\'' +
                '}';
    }
}
