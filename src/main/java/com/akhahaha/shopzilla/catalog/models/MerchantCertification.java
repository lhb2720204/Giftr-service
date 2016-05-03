package com.akhahaha.shopzilla.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * MerchantCertification model
 * Created by Alan on 5/2/2016.
 */
public class MerchantCertification {
    private List<Image> images;
    private Boolean certified;
    private String level;

    @JsonProperty("image")
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Boolean getCertified() {
        return certified;
    }

    public void setCertified(Boolean certified) {
        this.certified = certified;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
