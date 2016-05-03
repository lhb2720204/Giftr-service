package com.akhahaha.shopzilla.catalog.models;

/**
 * Image model
 * Created by Alan on 5/2/2016.
 */
public class Image {
    private String value;
    private Long xsize;
    private Long ysize;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getXsize() {
        return xsize;
    }

    public void setXsize(Long xsize) {
        this.xsize = xsize;
    }

    public Long getYsize() {
        return ysize;
    }

    public void setYsize(Long ysize) {
        this.ysize = ysize;
    }
}
