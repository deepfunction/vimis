//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3
// See https://eclipse-ee4j.github.io/jaxb-ri
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2021.09.22 at 03:45:37 PM MSK
//

package ru.akrtkv.egisz.vimis.dto.vimis;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "returnMsgId", propOrder = {
        "msgId",
        "error"
})
public class ReturnMsgId {

    @XmlElement(name = "msg_id", required = true)
    protected String msgId;

    protected String error;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String value) {
        this.msgId = value;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ReturnMsgId{" +
                "msgId='" + msgId + '\'' +
                '}';
    }
}
