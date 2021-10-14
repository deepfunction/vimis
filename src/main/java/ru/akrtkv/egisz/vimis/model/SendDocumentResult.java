package ru.akrtkv.egisz.vimis.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "send_document_result")
public class SendDocumentResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date", nullable = false, columnDefinition = "timestamp default now()")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime creationDate;

    @Column(name = "message_id", unique = true)
    private String messageId;

    @Column(name = "status_sync")
    private String statusSync;

    @Column(name = "status_async")
    private String statusAsync;

    @Column(name = "description")
    @Type(type = "text")
    private String description;

    @Column(name = "mis_result_delivery_status")
    private String misResultDeliveryStatus;

    @Column(name = "mis_result_delivery_error")
    private String misResultDeliveryError;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(String statusSync) {
        this.statusSync = statusSync;
    }

    public String getStatusAsync() {
        return statusAsync;
    }

    public void setStatusAsync(String statusAsync) {
        this.statusAsync = statusAsync;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMisResultDeliveryStatus() {
        return misResultDeliveryStatus;
    }

    public void setMisResultDeliveryStatus(String misResultDeliveryStatus) {
        this.misResultDeliveryStatus = misResultDeliveryStatus;
    }

    public String getMisResultDeliveryError() {
        return misResultDeliveryError;
    }

    public void setMisResultDeliveryError(String misResultDeliveryError) {
        this.misResultDeliveryError = misResultDeliveryError;
    }
}
