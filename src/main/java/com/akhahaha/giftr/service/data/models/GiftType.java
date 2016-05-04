package com.akhahaha.giftr.service.data.models;

/**
 * GiftType model
 * Created by Alan on 4/29/2016.
 */
public class GiftType {
    public static final GiftType UNKNOWN = new GiftType(1, "Unknown");
    public static final GiftType PRACTICAL = new GiftType(2, "Practical");
    public static final GiftType GAG = new GiftType(3, "Gag");
    public static final GiftType BOTH = new GiftType(4, "Both");

    private Integer id;
    private String name;

    public GiftType() {
    }

    public GiftType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
