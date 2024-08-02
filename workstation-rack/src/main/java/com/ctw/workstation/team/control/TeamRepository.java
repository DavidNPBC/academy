package com.ctw.workstation.team.control;


import com.ctw.workstation.team.entity.mappers.MapperTeam;
import com.ctw.workstation.team.entity.dto.TeamDTO;
import com.ctw.workstation.team.entity.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {

    @Transactional
    public TeamDTO save(TeamDTO dto) {

        Team team = MapperTeam.dtoToTeam(dto);

        persist(team);
        dto.setId(team.getId());

        return dto;
    }

    @Transactional
    public TeamDTO getById(Long id) {
        Team team = findById(id);

        if (team == null) {
            return null;
        }

        TeamDTO dto = MapperTeam.teamToDto(team);

        return dto;
    }

    @Transactional
    public List<TeamDTO> listAllTeams() {

        List<Team> teamList = listAll();

        if (teamList.isEmpty()) {
            return new ArrayList<>();
        }

        List<TeamDTO> dtoList = teamList.stream().map(team -> MapperTeam.teamToDto(team)).toList();

        return dtoList;
    }

    @Transactional
    public TeamDTO update(Long id, TeamDTO updatedDto) {

        Team existingTeam = findById(id);

        if (existingTeam != null) {
            existingTeam = MapperTeam.updateTeam(existingTeam, updatedDto);

            persist(existingTeam);
            updatedDto.setId(id);
            return updatedDto;
        } else {
            return null;
        }
    }

    @Transactional
    public TeamDTO delete(Long id) {
        Team team = findById(id);
        if (team == null) {
            return null;
        }

        team.clearTeamMembers();


        deleteById(id);

        return MapperTeam.teamToDto(team);
    }

}
