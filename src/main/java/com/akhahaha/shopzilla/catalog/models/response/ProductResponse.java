package com.akhahaha.shopzilla.catalog.models.response;

import com.akhahaha.shopzilla.catalog.models.Classification;
import com.akhahaha.shopzilla.catalog.models.ProductResult;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ProductResponse model
 * Created by Alan on 5/2/2016.
 */
public class ProductResponse {
    private Classification classification;
    private ProductResult productResult;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    @JsonProperty("products")
    public ProductResult getProductResult() {
        return productResult;
    }

    public void setProductResult(ProductResult productResult) {
        this.productResult = productResult;
    }
}
