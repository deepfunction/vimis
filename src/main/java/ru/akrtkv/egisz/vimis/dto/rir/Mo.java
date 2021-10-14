package ru.akrtkv.egisz.vimis.dto.rir;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Mo {

    @NotNull
    @NotEmpty
    private String oid;

    @NotNull
    @NotEmpty
    private String nameShort;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }
}
