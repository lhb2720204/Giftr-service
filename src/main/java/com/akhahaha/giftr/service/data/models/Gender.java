package com.akhahaha.giftr.service.data.models;

import com.akhahaha.giftr.service.View;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Gender model
 * Created by Alan on 4/29/2016.
 */
public class Gender {
    public static final Gender UNKNOWN = new Gender(1, "Unknown");
    public static final Gender MALE = new Gender(2, "Male");
    public static final Gender FEMALE = new Gender(3, "Female");

    @JsonView(View.Summary.class)
    private Integer id;
    @JsonView(View.Summary.class)
    private String name;

    public Gender() {
    }

    public Gender(Integer id) {
        this.id = id;
        switch (id) {
            case 1:
                this.name = "Unknown";
                break;
            case 2:
                this.name = "Male";
                break;
            case 3:
                this.name = "Female";
                break;
            default:
                this.name = "Unknown";
                break;
        }
    }

    public Gender(Integer id, String name) {
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
