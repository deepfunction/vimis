package ru.akrtkv.egisz.vimis.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate date) {
        return date.format(dateFormat);
    }

    @Override
    public LocalDate unmarshal(String date) {
        return LocalDate.parse(date, dateFormat);
    }
}
