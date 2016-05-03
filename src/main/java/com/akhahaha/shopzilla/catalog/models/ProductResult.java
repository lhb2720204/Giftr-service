package com.akhahaha.shopzilla.catalog.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ProductResult model
 * Created by Alan on 5/2/2016.
 */
public class ProductResult {
    private PriceSet priceSet;
    private List<Product> products;
    private Integer includedResults;
    private Integer totalResults;

    public PriceSet getPriceSet() {
        return priceSet;
    }

    public void setPriceSet(PriceSet priceSet) {
        this.priceSet = priceSet;
    }

    @JsonProperty("product")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getIncludedResults() {
        return includedResults;
    }

    public void setIncludedResults(Integer includedResults) {
        this.includedResults = includedResults;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
