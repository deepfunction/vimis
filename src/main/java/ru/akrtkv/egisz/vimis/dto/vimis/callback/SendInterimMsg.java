//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3
// See https://eclipse-ee4j.github.io/jaxb-ri
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2021.09.23 at 10:13:16 AM MSK
//

package ru.akrtkv.egisz.vimis.dto.vimis.callback;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sendInterimMsg", propOrder = {
        "msgId",
        "description"
})
public class SendInterimMsg {

    @XmlElement(name = "msg_id", required = true)
    protected String msgId;

    @XmlElement(required = true)
    protected String description;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String value) {
        this.msgId = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    @Override
    public String toString() {
        return "SendInterimMsg{" +
                "msgId='" + msgId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}