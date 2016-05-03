package com.akhahaha.shopzilla.catalog.models;

/**
 * Price model
 * Created by Alan on 5/2/2016.
 */
public class Price {
    private String value;
    private Integer integral;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}
