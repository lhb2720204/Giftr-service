package com.akhahaha.shopzilla.catalog.client;

import com.akhahaha.giftr.service.data.dao.queryBuilder.ProductQueryBuilder;
import com.akhahaha.shopzilla.catalog.models.Product;
import com.akhahaha.shopzilla.catalog.models.response.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.List;

/**
 * Shopzilla Catalog client implementation
 * Created by Alan on 5/3/2016.
 */
public class CatalogClientImpl implements CatalogClient {
    private static final String BASE_URL = "http://catalog.bizrate.com/services/catalog/v1/us/";

    private String publisherID;
    private String apiKey;

    public CatalogClientImpl() {
        // Use custom Jackson ObjectMapper for serialization (set globally)
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Product> searchProductsByAdvancedSearch(ProductQueryBuilder productQueryBuilder) {
        try {
            HttpResponse<ProductResponse> productHttpResponse = Unirest.get(BASE_URL + "{endpoint}")
                    .routeParam("endpoint", "product")
                    .queryString("apiKey", apiKey).queryString("publisherId", publisherID)
                    .queryString("format", "json")
                    .queryString(productQueryBuilder.buildParamMap())
                    .asObject(ProductResponse.class);
            return productHttpResponse.getBody().getProductResult().getProducts();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }
}