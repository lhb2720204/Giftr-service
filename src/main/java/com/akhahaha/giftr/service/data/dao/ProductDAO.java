package com.akhahaha.giftr.service.data.dao;

import com.akhahaha.giftr.service.data.models.Product;

import java.util.List;

/**
 * Product DAO interface
 * Created by Alan on 5/2/2016.
 */
public interface ProductDAO extends DAO {
    List<Product> searchProductsByKeyword(String keyword);
}
