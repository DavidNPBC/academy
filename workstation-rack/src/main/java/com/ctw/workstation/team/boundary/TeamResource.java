package com.ctw.workstation.team.boundary;

import com.ctw.workstation.shared.exceptions.CustomException;
import com.ctw.workstation.team.control.TeamRepository;
import com.ctw.workstation.team.entity.dto.TeamDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/teams")
public class TeamResource {

    @Inject
    private TeamRepository teamRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeam(TeamDTO teamDTO) {

        if (teamDTO.getProduct() == null || teamDTO.getDefaultLocation() == null || teamDTO.getName() == null) {

            String missing = teamDTO.getDefaultLocation() == null ? "Location" : teamDTO.getName() == null ? "Name" : "Product";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Missing information. " + missing + " is missing");
        }

        TeamDTO dto = teamRepository.save(teamDTO);

        if (dto == null) {
            return CustomException.throwCustomException(Response.Status.INTERNAL_SERVER_ERROR, "Failed to save the Team.");
        }

        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamById(@PathParam("id") Long id) {
        TeamDTO dto = teamRepository.getById(id);

        if (dto == null) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Invalid Team id: " + id);
        }

        return Response.status(Response.Status.OK).entity(dto).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeams() {
        List<TeamDTO> dtoList = teamRepository.listAllTeams();

        if (dtoList.isEmpty()) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "There are no Teams in the database.");
        }

        return Response.status(Response.Status.OK).entity(dtoList).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeam(@PathParam("id") Long id, TeamDTO updatedDto) {

        TeamDTO retrievedDto = teamRepository.update(id, updatedDto);

        if (retrievedDto == null) {

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "A Team with id " + id + " was not found.");
        }

        return Response.status(Response.Status.OK).entity(updatedDto).build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeam(@PathParam("id") Long id) {

        TeamDTO teamDTO = teamRepository.delete(id);

        if (teamDTO == null) {
            return  CustomException.throwCustomException(Response.Status.NOT_FOUND, "Team with id: " + id + " not found");

        }

        return CustomException.throwCustomException(Response.Status.OK, "Team with id: " + id + " deleted");


    }


}
