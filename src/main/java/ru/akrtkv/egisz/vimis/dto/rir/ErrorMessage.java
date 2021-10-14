package ru.akrtkv.egisz.vimis.dto.rir;

public class ErrorMessage {

    private String messageId;

    private String statusSync;

    private String statusAsync;

    private String error;

    private String misResultDeliveryStatus;

    private String misResultDeliveryError;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
