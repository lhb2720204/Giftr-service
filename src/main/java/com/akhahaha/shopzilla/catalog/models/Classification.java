package com.akhahaha.shopzilla.catalog.models;

/**
 * Classification model
 * Created by Alan on 5/2/2016.
 */
public class Classification {
    private String relevancyScore;
    private URL searchUrl;

    public String getRelevancyScore() {
        return relevancyScore;
    }

    public void setRelevancyScore(String relevancyScore) {
        this.relevancyScore = relevancyScore;
    }

    public URL getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(URL searchUrl) {
        this.searchUrl = searchUrl;
    }
}
