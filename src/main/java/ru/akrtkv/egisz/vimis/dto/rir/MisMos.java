package ru.akrtkv.egisz.vimis.dto.rir;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MisMos {

    @NotNull
    @NotEmpty
    private String misUuid;

    @NotNull
    private List<@Valid Mo> moList;

    @NotNull
    @NotEmpty
    private String url;

    public String getMisUuid() {
        return misUuid;
    }

    public void setMisUuid(String misUuid) {
        this.misUuid = misUuid;
    }

    public List<Mo> getMoList() {
        return moList;
    }

    public void setMoList(List<Mo> moList) {
        this.moList = moList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
