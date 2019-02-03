package com.birlasoft.priceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private double was;
    private double then1;
    private double then2;
    private NowPrice nowPrice;
    private String currency;
    private String nowPrice1;

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

    public NowPrice getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(NowPrice nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNowPrice1() {
        return nowPrice1;
    }

    public void setNowPrice1(String nowPrice1) {
        this.nowPrice1 = this.currency + this.nowPrice.getTo();
    }
}
