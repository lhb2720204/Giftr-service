package com.akhahaha.giftr.service.data.models;

/**
 * ProductSource model
 * Created by Alan on 5/2/2016.
 */
public class ProductSource {
    public static final ProductSource CONNEXITY = new ProductSource(1, "Connexity");

    private Integer id;
    private String name;

    public ProductSource(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
