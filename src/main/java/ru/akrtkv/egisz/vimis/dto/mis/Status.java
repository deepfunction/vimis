package ru.akrtkv.egisz.vimis.dto.mis;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    SUCCESS("success"),

    ERROR("error");

    private final String value;

    Status(String v) {
        value = v;
    }

    public static Status fromValue(String v) {
        for (Status c : Status.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public String value() {
        return value;
    }
}
