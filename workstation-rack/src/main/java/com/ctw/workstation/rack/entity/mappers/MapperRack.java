package com.ctw.workstation.rack.entity.mappers;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.entity.dto.RackDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;

public class MapperRack {
    public static Rack dtoToRack(RackDTO dto){
        Rack rack = new Rack();
        rack.setStatus(dto.getStatus());
        rack.setSerialNumber(dto.getSerialNumber());
        rack.setDefaultLocation(dto.getDefaultLocation());
        return rack;
    }

    public static RackDTO rackToDto(Rack rack){
        RackDTO dto = new RackDTO();
        dto.setId(rack.getId());
        dto.setDefaultLocation(rack.getDefaultLocation());
        dto.setStatus(rack.getStatus());
        dto.setSerialNumber(rack.getSerialNumber());

        if(rack.getTeam() == null) {
            dto.setTeamId(null);
        } else {
            dto.setTeamId(rack.getTeam().getId());
        }


        return dto;
    }

    public static Rack updateRack(Rack rack, RackDTO dto){
        rack.setStatus(dto.getStatus());
        rack.setSerialNumber(dto.getSerialNumber());
        rack.setDefaultLocation(dto.getDefaultLocation());

        if(rack.getTeam() == null) {
            dto.setTeamId(null);
        } else {
            dto.setTeamId(rack.getTeam().getId());
        }

        return rack;
    }
}
