package com.ctw.workstation.team.entity.mappers;


import com.ctw.workstation.team.entity.dto.TeamDTO;
import com.ctw.workstation.team.entity.Team;

public class MapperTeam {

    public static Team dtoToTeam(TeamDTO dto) {
        Team team = new Team();
        team.setProduct(dto.getProduct());
        team.setName(dto.getName());
        team.setDefaultLocation(dto.getDefaultLocation());
        return team;
    }

    public static TeamDTO teamToDto(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setDefaultLocation(team.getDefaultLocation());
        dto.setProduct(team.getProduct());
        return dto;
    }

    public static Team updateTeam(Team team, TeamDTO dto) {
        team.setName(dto.getName());
        team.setDefaultLocation(dto.getDefaultLocation());
        team.setProduct(dto.getProduct());
        return team;
    }
}
