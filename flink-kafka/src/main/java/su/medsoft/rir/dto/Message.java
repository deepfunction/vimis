package ru.akrtkv.rir.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Message {

    private String requestType;

    private String misId;

    private String messageId;

    private String moOid;

    private Integer vimisType;

    private Integer docType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dateTime;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getMisId() {
        return misId;
    }

    public void setMisId(String misId) {
        this.misId = misId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMoOid() {
        return moOid;
    }

    public void setMoOid(String moOid) {
        this.moOid = moOid;
    }

    public Integer getVimisType() {
        return vimisType;
    }

    public void setVimisType(Integer vimisType) {
        this.vimisType = vimisType;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
