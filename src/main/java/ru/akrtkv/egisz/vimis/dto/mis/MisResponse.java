package ru.akrtkv.egisz.vimis.dto.mis;

public class MisResponse {

    private Status status;

    private String error;

    public MisResponse() {
    }

    public MisResponse(Status status, String error) {
        this.status = status;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", error='" + error + '\'' +
                '}';
    }
}
