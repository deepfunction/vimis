package ru.akrtkv.egisz.vimis.dto.rir;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Signature {

    @Schema(description = "Файл подписи в base64")
    @NotEmpty
    @NotNull
    private String data;

    @Schema(description = "Контрольная сумма по CRC-32-IEEE 802.3 в десятичном виде")
    @NotNull
    private Integer checksum;

    public String getData() {
        return data;
    }

    public void setData(String value) {
        this.data = value;
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(Integer value) {
        this.checksum = value;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "data='" + data + '\'' +
                ", checksum=" + checksum +
                '}';
    }
}
