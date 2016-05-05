package com.akhahaha.giftr.service.controllers;

import com.akhahaha.giftr.service.data.dao.DAOManager;
import com.akhahaha.giftr.service.data.dao.ProductDAO;
import com.akhahaha.giftr.service.data.models.Product;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Products service controller
 * Created by Alan on 5/3/2016.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    private ProductDAO productDAO = (ProductDAO) DAOManager.getInstance().getDAO(DAOManager.DAOType.PRODUCT);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false) String keyword) {
        // TODO Validate credentials
        // TODO Add more search fields
        List<Product> products = productDAO.searchProductsByKeyword(keyword);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand().toUri());
        return new ResponseEntity<>(products, headers, HttpStatus.OK);
    }
}
