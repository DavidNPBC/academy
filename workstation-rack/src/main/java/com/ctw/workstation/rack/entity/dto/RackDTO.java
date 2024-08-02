package com.ctw.workstation.rack.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RackDTO {
    private Long id;
    @JsonProperty("serial_number")
    private String serialNumber;
    @JsonProperty("team_id")
    private Long teamId;
    @JsonProperty("default_location")
    private String defaultLocation;
    private String status;

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
