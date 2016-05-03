package com.akhahaha.shopzilla.catalog.client;

import com.akhahaha.shopzilla.catalog.models.Product;

import java.util.List;

/**
 * Shopzilla Catalog client interface
 * Created by Alan on 5/3/2016.
 */
public interface CatalogClient {
    List<Product> searchProductsByKeyword(String keyword);
}
