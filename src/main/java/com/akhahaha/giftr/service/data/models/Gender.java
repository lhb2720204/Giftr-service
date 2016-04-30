package com.akhahaha.giftr.service.data.models;

/**
 * Gender model
 * Created by Alan on 4/29/2016.
 */
public class Gender {
    public static final Gender UNKNOWN = new Gender(1, "Unknown");
    public static final Gender MALE = new Gender(2, "Male");
    public static final Gender FEMALE = new Gender(3, "Female");

    private Integer id;
    private String name;

    public Gender(Integer id, String name) {
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
