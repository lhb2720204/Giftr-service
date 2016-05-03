package com.akhahaha.shopzilla.catalog.models;

/**
 * PriceSet model
 * Created by Alan on 5/2/2016.
 */
public class PriceSet {
    private Price minPrice;
    private Price maxPrice;

    public Price getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Price minPrice) {
        this.minPrice = minPrice;
    }

    public Price getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Price maxPrice) {
        this.maxPrice = maxPrice;
    }
}
