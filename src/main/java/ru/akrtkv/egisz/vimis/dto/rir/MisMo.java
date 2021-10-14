package ru.akrtkv.egisz.vimis.dto.rir;

public class MisMo {

    private Long id;

    private String misUuid;

    private String moOid;

    private String moName;

    private String status;

    private String disablingReason;

    private boolean disabled;

    private String misUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMisUuid() {
        return misUuid;
    }

    public void setMisUuid(String misUuid) {
        this.misUuid = misUuid;
    }

    public String getMoOid() {
        return moOid;
    }

    public void setMoOid(String moOid) {
        this.moOid = moOid;
    }

    public String getMoName() {
        return moName;
    }

    public void setMoName(String moName) {
        this.moName = moName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisablingReason() {
        return disablingReason;
    }

    public void setDisablingReason(String disablingReason) {
        this.disablingReason = disablingReason;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getMisUrl() {
        return misUrl;
    }

    public void setMisUrl(String misUrl) {
        this.misUrl = misUrl;
    }
}
