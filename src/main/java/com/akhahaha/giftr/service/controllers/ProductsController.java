package com.akhahaha.giftr.service.controllers;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.ProductDAO;
import com.akhahaha.giftr.service.data.dao.queryBuilder.ProductQueryBuilder;
import com.akhahaha.giftr.service.data.models.Gender;
import com.akhahaha.giftr.service.data.models.GiftType;
import com.akhahaha.giftr.service.data.models.Product;
import com.akhahaha.giftr.service.data.models.UserStatus;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;	

/**
 * Products service controller
 * Created by Alan on 5/3/2016.
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductsController {
    private ProductDAO productDAO = (ProductDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.PRODUCT);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer start,
            @RequestParam(required = false) Integer numResults,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String sort) {
    	
    	ProductQueryBuilder productQueryBuilder = new ProductQueryBuilder();
    	setProductQueryFields(productQueryBuilder, keyword, start, numResults, minPrice, maxPrice, sort);
    	
        List<Product> products = productDAO.searchProductsByAdvancedSearch(productQueryBuilder);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(products, headers, HttpStatus.OK);
    }
    
    private void setProductQueryFields(ProductQueryBuilder productQueryBuilder,
    		String keyword, Integer start, Integer numResults, Integer minPrice, Integer maxPrice, String sort) {
        if (keyword != null) productQueryBuilder.setKeyword(keyword);
        if (start != null) productQueryBuilder.setStart(start);
        if (numResults != null) productQueryBuilder.setNumResults(numResults);
        if (minPrice != null) productQueryBuilder.setMinPrice(minPrice);
        if (maxPrice != null) productQueryBuilder.setMaxPrice(maxPrice);
        if (sort != null) productQueryBuilder.setSort(sort);
    }
    
}
