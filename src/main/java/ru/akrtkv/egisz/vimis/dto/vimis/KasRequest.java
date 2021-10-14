package ru.akrtkv.egisz.vimis.dto.vimis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class KasRequest {

    @NotNull
    @NotEmpty
    @JsonProperty("mo_oid")
    protected String moOid;

    @NotNull
    protected Integer year;

    @NotNull
    protected String region;

    public String getMoOid() {
        return moOid;
    }

    public void setMoOid(String moOid) {
        this.moOid = moOid;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "KasRequest{" +
                "moOid='" + moOid + '\'' +
                ", year=" + year +
                ", region=" + region +
                '}';
    }
}
