package com.ctw.workstation.rackasset.control;


import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rackasset.entity.mappers.MapperRackAsset;
import com.ctw.workstation.rackasset.entity.dto.RackAssetDTO;
import com.ctw.workstation.rackasset.entity.RackAsset;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class RackAssetRepository implements PanacheRepository<RackAsset> {

    @Transactional
    public RackAssetDTO save(RackAssetDTO dto) {
        Rack rack = getEntityManager().find(Rack.class, dto.getRackId());

        if (rack == null) {
            return null;
        } else {
            RackAsset rackAsset = MapperRackAsset.dtoToRackAsset(dto);
            rack.addRackAsset(rackAsset);

            persist(rackAsset);
            dto.setId(rackAsset.getId());
            return dto;
        }
    }

    @Transactional
    public RackAssetDTO getById(Long id) {

        RackAsset rackAsset = findById(id);

        if (rackAsset == null) {
            return null;
        }
        RackAssetDTO dto = MapperRackAsset.rackAssetToDto(rackAsset);

        return dto;
    }

    @Transactional
    public List<RackAssetDTO> listAllRackAssets() {

        List<RackAsset> rackAssetList = listAll();

        if (rackAssetList.isEmpty()) {
            return new ArrayList<>();
        }

        List<RackAssetDTO> dtoList = rackAssetList.stream().map(rackAsset -> MapperRackAsset.rackAssetToDto(rackAsset)).toList();

        return dtoList;
    }

    @Transactional
    public RackAssetDTO update(Long id, RackAssetDTO updatedDto) {
        Rack rack = getEntityManager().find(Rack.class, updatedDto.getRackId());

        if (rack == null) {
            updatedDto.setRackId(null);
            return updatedDto;

        }

        RackAsset existingRackAsset = findById(id);

        if (existingRackAsset != null) {
            existingRackAsset = MapperRackAsset.updateRackAsset(existingRackAsset, updatedDto);

            persist(existingRackAsset);
            updatedDto.setId(id);
            return updatedDto;
        } else {
            return null;
        }
    }

    @Transactional
    public RackAssetDTO delete(Long id) {
        RackAsset rackAsset = findById(id);
        if (rackAsset == null) {
            return null;
        }
        deleteById(id);

        return MapperRackAsset.rackAssetToDto(rackAsset);
    }
}
