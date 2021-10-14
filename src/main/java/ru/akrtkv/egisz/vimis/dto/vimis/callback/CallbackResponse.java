package ru.akrtkv.egisz.vimis.dto.vimis.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "callbackResponse", propOrder = {
        "status"
})
public class CallbackResponse {

    protected int status;

    public CallbackResponse() {
    }

    public CallbackResponse(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CallbackResponse{" +
                "status=" + status +
                '}';
    }
}
