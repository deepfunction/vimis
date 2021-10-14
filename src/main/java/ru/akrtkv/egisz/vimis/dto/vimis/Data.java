package ru.akrtkv.egisz.vimis.dto.vimis;

public class Data {

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Data{" +
                "number='" + number + '\'' +
                '}';
    }
}
