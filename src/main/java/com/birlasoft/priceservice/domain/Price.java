package com.birlasoft.priceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private double was;
    private double then1;
    private double then2;
    private double now;
    private String currency;

    public double getWas() {
        return was;
    }

    public void setWas(double was) {
        this.was = was;
    }

    public double getThen1() {
        return then1;
    }

    public void setThen1(double then1) {
        this.then1 = then1;
    }

    public double getThen2() {
        return then2;
    }

    public void setThen2(double then2) {
        this.then2 = then2;
    }

    public double getNow() {
        return now;
    }

    public void setNow(double now) {
        this.now = now;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
