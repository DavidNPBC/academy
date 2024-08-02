package com.ctw.workstation.team.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamDTO {
    private Long id;
    private String name;
    private String product;
    @JsonProperty("default_location")
    private String defaultLocation;

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
