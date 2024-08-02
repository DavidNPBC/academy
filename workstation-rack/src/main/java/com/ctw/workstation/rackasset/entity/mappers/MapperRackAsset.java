package com.ctw.workstation.rackasset.entity.mappers;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rackasset.entity.RackAsset;
import com.ctw.workstation.rackasset.entity.dto.RackAssetDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;

public class MapperRackAsset {
    public static RackAsset dtoToRackAsset(RackAssetDTO dto){
        RackAsset rackAsset = new RackAsset();
        rackAsset.setAssetTag(dto.getAssetTag());
        return rackAsset;
    }

    public static RackAssetDTO rackAssetToDto(RackAsset rackAsset){
        RackAssetDTO dto = new RackAssetDTO();
        dto.setId(rackAsset.getId());
        dto.setAssetTag(rackAsset.getAssetTag());
        if(rackAsset.getRack() == null) {
            dto.setRackId(null);
        } else {
            dto.setRackId(rackAsset.getRack().getId());
        }

        return dto;
    }

    public static RackAsset updateRackAsset(RackAsset rackAsset, RackAssetDTO dto){
        rackAsset.setAssetTag(dto.getAssetTag());

        if(rackAsset.getRack() == null) {
            dto.setRackId(null);
        } else {
            dto.setRackId(rackAsset.getRack().getId());
        }
        return rackAsset;
    }
}
