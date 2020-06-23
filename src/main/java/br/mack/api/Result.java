package br.mack.api;

import java.io.Serializable;

public class Result implements Serializable {
    String date;
    Double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
