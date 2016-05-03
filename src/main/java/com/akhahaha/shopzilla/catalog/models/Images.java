package com.akhahaha.shopzilla.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Images model
 * Created by Alan on 5/3/2016.
 */
public class Images {
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    @JsonProperty("image")
    public void setImages(List<Image> images) {
        this.images = images;
    }
}
