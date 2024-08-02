package com.ctw.workstation.rackasset.entity.dto;

import com.ctw.workstation.rack.entity.Rack;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RackAssetDTO {
    private Long id;
    @JsonProperty("asset_tag")
    private String assetTag;
    @JsonProperty("rack_id")
    private Long rackId;

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
