package com.ctw.workstation.rackasset.boundary;

import com.ctw.workstation.rackasset.control.RackAssetRepository;
import com.ctw.workstation.rackasset.entity.dto.RackAssetDTO;
import com.ctw.workstation.shared.exceptions.CustomException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@Path("/rackasset")
public class RackAssetResource {


    @Inject
    private RackAssetRepository rackAssetRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRackAsset(RackAssetDTO rackAssetDTO) {
        if (rackAssetDTO.getAssetTag() == null) {
            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Missing information. Asset Tag is missing");
        }

        RackAssetDTO assetDTO = rackAssetRepository.save(rackAssetDTO);

        if (assetDTO.getRackId() == null) {
            assetDTO.setRackId(null);
            return Response.status(Response.Status.OK).entity(Arrays.asList(assetDTO, "Created a Rack Asset without a Rack")).build();
        }

        return Response.status(Response.Status.CREATED).entity(assetDTO).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRackAssetsById(@PathParam("id") Long id) {
        RackAssetDTO assetDTO = rackAssetRepository.getById(id);

        if(assetDTO == null){
           return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Invalid Rack Asset id: " + id);
        }
        return Response.status(Response.Status.OK).entity(assetDTO).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRackAssets() {
        List<RackAssetDTO> listAssetDTO = rackAssetRepository.listAllRackAssets();

        if(listAssetDTO.isEmpty()) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "There are no Rack Assets in the database.");
        }

        return Response.status(Response.Status.OK).entity(listAssetDTO).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRackAsset(@PathParam("id") Long id, RackAssetDTO updatedRackAsset) {
        Long savedId = updatedRackAsset.getRackId();

        if (updatedRackAsset.getAssetTag() == null) {

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Invalid data: Asset Tag is invalid");
        }

        RackAssetDTO assetDTO = rackAssetRepository.update(id, updatedRackAsset);

        if(assetDTO == null){
            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "A Rack Asset with id " + id + " was not found.");
        }

        if(assetDTO.getRackId() == null) {
            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Rack id: " + savedId + " not found. A Rack Asset must have a Rack.");
        }

        return Response.status(Response.Status.OK).entity(assetDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRackAsset(@PathParam("id") Long id) {

        RackAssetDTO assetDTO = rackAssetRepository.delete(id);

        if(assetDTO == null){
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Rack Asset with id: " + id + " not found");
        }
        return CustomException.throwCustomException(Response.Status.OK, "Rack Asset with id: " + id + " deleted");
    }

}
