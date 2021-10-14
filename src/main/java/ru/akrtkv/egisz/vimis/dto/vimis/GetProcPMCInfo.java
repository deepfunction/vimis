package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getProcPMCInfo")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id"
})
public class GetProcPMCInfo {

    protected String id;

    public GetProcPMCInfo() {
    }

    public GetProcPMCInfo(String id) {
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
