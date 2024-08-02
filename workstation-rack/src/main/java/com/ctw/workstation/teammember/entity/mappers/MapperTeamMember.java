package com.ctw.workstation.teammember.entity.mappers;

import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;

public class MapperTeamMember {
    public static TeamMember dtoToTeamMember(TeamMemberDTO dto){
        TeamMember teamMember = new TeamMember();
        teamMember.setName(dto.getName());
        teamMember.setCtwId(dto.getCtwId());
        return teamMember;
    }

    public static TeamMemberDTO teamMemberToDto(TeamMember member){
        TeamMemberDTO dto = new TeamMemberDTO();
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setCtwId(member.getCtwId());

        if(member.getTeam() == null) {
            dto.setTeamId(null);
        } else {
            dto.setTeamId(member.getTeam().getId());
        }

        return dto;
    }

    public static TeamMember updateTeamMember(TeamMember member, TeamMemberDTO dto){
        member.setName(dto.getName());
        member.setCtwId(dto.getCtwId());

        if(member.getTeam() == null) {
            dto.setTeamId(null);
        } else {
            dto.setTeamId(member.getTeam().getId());
        }

        return member;
    }
}
