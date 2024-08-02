package com.ctw.workstation.rack.control;


import com.ctw.workstation.rack.entity.mappers.MapperRack;
import com.ctw.workstation.rack.entity.dto.RackDTO;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.team.entity.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;



@ApplicationScoped
public class RackRepository implements PanacheRepository<Rack> {
    @Transactional
    public RackDTO save(RackDTO dto) {
        Team team = getEntityManager().find(Team.class, dto.getTeamId());

        Rack rack = MapperRack.dtoToRack(dto);

        if (team == null) {

            dto.setTeamId(null);

            persist(rack);
            dto.setId(rack.getId());

            return dto;
        } else {

            team.addRack(rack);

            persist(rack);

            dto.setId(rack.getId());

            return dto;
        }

    }

    @Transactional
    public RackDTO getById(Long id) {

        Rack rack = findById(id);

        if (rack == null) {
            return null;
        }

        RackDTO dto = MapperRack.rackToDto(rack);

        return dto;
    }

    @Transactional
    public List<RackDTO> listAllRacks() {

        List<Rack> rackList = listAll();

        if (rackList.isEmpty()) {
            return new ArrayList<>();
        }

        List<RackDTO> dtoList = rackList.stream().map(member -> MapperRack.rackToDto(member)).toList();

        return dtoList;
    }

    @Transactional
    public RackDTO update(Long id, RackDTO updatedDto) {
        Rack existingRack = findById(id);

        if (existingRack == null) {
            updatedDto.setTeamId(null);
            return updatedDto;
        }

        Team team = getEntityManager().find(Team.class, updatedDto.getTeamId());

        // new team exists and the object already has a team
        if (team != null && existingRack.getTeam() != null) {
            Team existingTeam = existingRack.getTeam();
            existingTeam.removeRack(existingRack);
            team.addRack(existingRack);
        }

        // new team exists and object had no prior team
        if (team != null && existingRack.getTeam() == null) {
            team.addRack(existingRack);
        }

        // new team exists and object had no prior team
        if (team == null && existingRack.getTeam() != null) {
            existingRack.getTeam().removeRack(existingRack);
        }

        Rack updatedRack = MapperRack.updateRack(existingRack, updatedDto);

        persist(updatedRack);
        updatedDto.setId(id);
        return updatedDto;
    }

    @Transactional
    public RackDTO delete(Long id) {
        Rack rack = findById(id);
        if (rack == null) {
            return null;
        }
        deleteById(id);

        return MapperRack.rackToDto(rack);
    }


}
