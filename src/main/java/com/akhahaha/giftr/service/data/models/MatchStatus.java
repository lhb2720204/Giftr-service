package com.akhahaha.giftr.service.data.models;

/**
 * MatchStatus model
 * Created by Alan on 4/29/2016.
 */
public class MatchStatus {
    public static final MatchStatus PENDING = new MatchStatus(1, "Pending");
    public static final MatchStatus COMPLETED = new MatchStatus(2, "Completed");

    private Integer id;
    private String name;

    public MatchStatus() {
    }

    public MatchStatus(Integer id) {
        this.id = id;
        switch (id) {
            case 1:
                this.name = "Pending";
                break;
            case 2:
                this.name = "Completed";
                break;
            default:
                this.name = "Pending";
                break;
        }
    }

    public MatchStatus(Integer id, String name) {
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
