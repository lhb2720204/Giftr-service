package com.akhahaha.giftr.service.data.models;

import java.math.BigInteger;
import java.util.List;

/**
 * Product model
 * Created by Alan on 5/2/2016.
 */
public class Product {
    private ProductSource source;
    private BigInteger sourceProductID;
    private String title;
    private String brandName;
    private String description;
    private String url;
    private List<String> images;
    private Integer price;

    public Product(ProductSource source, BigInteger sourceProductID, String title, String brandName, String description,
                   String url, List<String> images, Integer price) {
        this.source = source;
        this.sourceProductID = sourceProductID;
        this.title = title;
        this.brandName = brandName;
        this.description = description;
        this.url = url;
        this.images = images;
        this.price = price;
    }

    public ProductSource getSource() {
        return source;
    }

    public void setSource(ProductSource source) {
        this.source = source;
    }

    public BigInteger getSourceProductID() {
        return sourceProductID;
    }

    public void setSourceProductID(BigInteger sourceProductID) {
        this.sourceProductID = sourceProductID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
