package com.ctw.workstation.teammember.control;


import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.entity.mappers.MapperTeamMember;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@ApplicationScoped
public class TeamMemberRepository implements PanacheRepository<TeamMember> {

    @Transactional
    public TeamMemberDTO save(TeamMemberDTO dto) {
        Team team = getEntityManager().find(Team.class, dto.getTeamId());

        TeamMember teamMember = MapperTeamMember.dtoToTeamMember(dto);

        if (team == null) {

            dto.setTeamId(null);

            persist(teamMember);
            dto.setId(teamMember.getId());

            return dto;
        } else {

            team.addTeamMember(teamMember);

            persist(teamMember);

            dto.setId(teamMember.getId());

            return dto;
        }

    }

    @Transactional
    public TeamMemberDTO getById(Long id) {

        TeamMember member = findById(id);

        if (member == null) {
            return null;
        }

        TeamMemberDTO dto = MapperTeamMember.teamMemberToDto(member);

        return dto;
    }

    @Transactional
    public List<TeamMemberDTO> listAllTeamMembers() {
        List<TeamMember> memberList = listAll();

        if (memberList.isEmpty()) {
            return new ArrayList<>();
        }

        List<TeamMemberDTO> dtoList = memberList.stream().map(member -> MapperTeamMember.teamMemberToDto(member)).toList();

        return dtoList;
    }

    @Transactional
    public TeamMemberDTO update(Long id, TeamMemberDTO updatedDto) {

        TeamMember existingTeamMember = findById(id);
        if (existingTeamMember == null) {
            return null;
        }

        Team team = getEntityManager().find(Team.class, updatedDto.getTeamId());

        // new team exists and the object already has a team
        if (team != null && existingTeamMember.getTeam() != null) {
            Team existingTeam = existingTeamMember.getTeam();
            existingTeam.removeTeamMember(existingTeamMember);
            team.addTeamMember(existingTeamMember);
        }

        // new team exists and object had no prior team
        if (team != null && existingTeamMember.getTeam() == null) {
            team.addTeamMember(existingTeamMember);
        }

        // new team exists and object had a prior team
        if (team == null && existingTeamMember.getTeam() != null) {
            existingTeamMember.getTeam().removeTeamMember(existingTeamMember);
        }

        TeamMember updatedTeamMember = MapperTeamMember.updateTeamMember(existingTeamMember, updatedDto);

        persist(updatedTeamMember);
        updatedDto.setId(id);
        return updatedDto;
    }

    @Transactional
    public TeamMemberDTO delete(Long id) {
        TeamMember member = findById(id);
        if (member == null) {
            return null;
        }

        deleteById(id);

        return MapperTeamMember.teamMemberToDto(member);
    }

}
