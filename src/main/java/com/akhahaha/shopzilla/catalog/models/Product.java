package com.akhahaha.shopzilla.catalog.models;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Product model
 * Created by Alan on 5/2/2016.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Product {
    private String type;
    private Brand brand;
    private String title;
    private String description;
    private String manufacturer;
    private URL url;
    private Images images;
    private String sku;
    private String upc;
    private URL detailUrl;
    private Price price;
    private Price originalPrice;
    private Integer markdownPercent;
    private Boolean bidded;
    private String merchantProductId;
    private String merchantName;
    private MerchantRating merchantRating;
    private MerchantCertification merchantCertification;
    private String merchantLogoUrl;
    private String condition;
    private String stock;
    private Price shipAmount;
    private String shipType;
    private BigInteger shipWeight;
    private BigDecimal relevancy;
    private BigInteger categoryId;
    private BigInteger merchantId;
    private BigInteger id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public URL getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(URL detailUrl) {
        this.detailUrl = detailUrl;
    }

    public Price getPrice() {
    	if (price != null)
    		return price;
    	else {
    		Price dummy = new Price();
    		dummy.setValue("Check at orignal site");
    		dummy.setIntegral(-1);
    		return dummy;
    	}
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Price originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getMarkdownPercent() {
        return markdownPercent;
    }

    public void setMarkdownPercent(Integer markdownPercent) {
        this.markdownPercent = markdownPercent;
    }

    public Boolean getBidded() {
        return bidded;
    }

    public void setBidded(Boolean bidded) {
        this.bidded = bidded;
    }

    public String getMerchantProductId() {
        return merchantProductId;
    }

    public void setMerchantProductId(String merchantProductId) {
        this.merchantProductId = merchantProductId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public MerchantRating getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(MerchantRating merchantRating) {
        this.merchantRating = merchantRating;
    }

    public MerchantCertification getMerchantCertification() {
        return merchantCertification;
    }

    public void setMerchantCertification(MerchantCertification merchantCertification) {
        this.merchantCertification = merchantCertification;
    }

    public String getMerchantLogoUrl() {
        return merchantLogoUrl;
    }

    public void setMerchantLogoUrl(String merchantLogoUrl) {
        this.merchantLogoUrl = merchantLogoUrl;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Price getShipAmount() {
        return shipAmount;
    }

    public void setShipAmount(Price shipAmount) {
        this.shipAmount = shipAmount;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public BigInteger getShipWeight() {
        return shipWeight;
    }

    public void setShipWeight(BigInteger shipWeight) {
        this.shipWeight = shipWeight;
    }

    public BigDecimal getRelevancy() {
        return relevancy;
    }

    public void setRelevancy(BigDecimal relevancy) {
        this.relevancy = relevancy;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    public BigInteger getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(BigInteger merchantId) {
        this.merchantId = merchantId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
