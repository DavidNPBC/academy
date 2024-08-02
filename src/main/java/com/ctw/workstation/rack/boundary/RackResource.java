package com.ctw.workstation.rack.boundary;


import com.ctw.workstation.rack.control.RackRepository;
import com.ctw.workstation.rack.entity.dto.RackDTO;
import com.ctw.workstation.shared.exceptions.CustomException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;


@Path("/racks")
public class RackResource {

    @Inject
    private RackRepository rackRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRack(RackDTO rackDTO) {
        if (rackDTO.getSerialNumber() == null || rackDTO.getStatus() == null || rackDTO.getDefaultLocation() == null) {

            String missing = rackDTO.getStatus() == null ? "Serial Number" : rackDTO.getStatus() == null ? "Status" : "Location";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Missing information. " + missing + " is missing");
        }

        RackDTO dto = rackRepository.save(rackDTO);

        if (dto.getTeamId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(dto, "Created a Rack without a Team")).build();
        }

        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRacksById(@PathParam("id") Long id) {
        RackDTO rackDTO = rackRepository.getById(id);

        if (rackDTO == null) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Invalid Rack id: " + id);
        }

        return Response.status(Response.Status.OK).entity(rackDTO).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRacks() {
        List<RackDTO> rackList = rackRepository.listAllRacks();

        if (rackList.isEmpty()) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "There are no Rack in the database.");
        }

        return Response.status(Response.Status.OK).entity(rackList).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRack(@PathParam("id") Long id, RackDTO updatedRack) {

        Long savedId = updatedRack.getTeamId();

        if (updatedRack.getStatus() == null || updatedRack.getSerialNumber() == null || updatedRack.getDefaultLocation() == null) {

            String invalid = updatedRack.getStatus() == null ? "Serial Number" : updatedRack.getStatus() == null ? "Status" : "Location";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Invalid data: " + invalid + " is invalid");
        }

        RackDTO retrievedRack = rackRepository.update(id, updatedRack);

        if (retrievedRack.getTeamId() == null) {
            return Response.status(Response.Status.OK).entity("The Team Id: " + savedId + ", that you entered is not valid. Other changes if any were saved.").build();
        }

        return Response.status(Response.Status.OK).entity(retrievedRack).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRack(@PathParam("id") Long id) {
        RackDTO rackDTO = rackRepository.delete(id);

        if (rackDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Rack with id: " + id + " not found").build();
        }
        return Response.status(Response.Status.OK).entity("Rack with id: " + id + " deleted").build();
    }


}
