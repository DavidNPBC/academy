package com.ctw.workstation.teammember.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamMemberDTO {
    private Long id;
    @JsonProperty("team_id")
    private Long teamId;
    @JsonProperty("ctw_id")
    private String ctwId;
    private String name;

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setCtwId(String ctwId) {
        this.ctwId = ctwId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getCtwId() {
        return ctwId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return "Name: " + name + " CtwID: " + ctwId + " TeamId: " + teamId + " ID: " + id;
    }
}
