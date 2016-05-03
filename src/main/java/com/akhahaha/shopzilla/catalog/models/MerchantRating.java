package com.akhahaha.shopzilla.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * MerchantRating model
 * Created by Alan on 5/3/2016.
 */
public class MerchantRating {
    private List<Image> images;
    private Double value;

    @JsonProperty("image")
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
