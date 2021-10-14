package ru.akrtkv.egisz.vimis.dto.vimis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class KasResponse {

    @JsonProperty("is_success")
    protected boolean success;

    protected Data data;

    protected List<String> errors;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "KasResponse{" +
                "data=" + data +
                ", success=" + success +
                ", errors=" + errors +
                '}';
    }
}
