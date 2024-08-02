package com.ctw.workstation.teammember.boundary;

import com.ctw.workstation.shared.exceptions.CustomException;
import com.ctw.workstation.teammember.control.TeamMemberRepository;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

@Path("/team-members")
public class TeamMemberResource {

    @Inject
    private TeamMemberRepository teamMemberRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTeamMember(TeamMemberDTO teamMemberDTO) {

        if (teamMemberDTO.getTeamId() == null || teamMemberDTO.getCtwId() == null || teamMemberDTO.getName() == null) {

            String missing = teamMemberDTO.getTeamId() == null ? "Team Id" : teamMemberDTO.getName() == null ? "Name" : "CTW id";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Missing information. " + missing + " is missing");
        }

        TeamMemberDTO teamMember = teamMemberRepository.save(teamMemberDTO);

        if (teamMember.getTeamId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(teamMember, "Created a Team Member without a Team")).build();
        }

        return Response.status(Response.Status.CREATED).entity(teamMember).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamMembersById(@PathParam("id") Long id) {

        TeamMemberDTO teamMember = teamMemberRepository.getById(id);

        if (teamMember == null) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Invalid Team Member id: " + id);
        }

        return Response.status(Response.Status.OK).entity(teamMember).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamMembers() {
        List<TeamMemberDTO> memberList = teamMemberRepository.listAllTeamMembers();

        if (memberList.isEmpty()) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "There are no Team Members in the database.");
        }

        return Response.status(Response.Status.OK).entity(memberList).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTeamMember(@PathParam("id") Long id, TeamMemberDTO updatedDto) {

        Long savedId = updatedDto.getTeamId();

        if (updatedDto.getCtwId() == null || updatedDto.getName() == null) {

            String invalid = updatedDto.getName() == null ? "Name" : "CTW id";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Invalid data: " + invalid + " is invalid");
        }

        TeamMemberDTO retrievedDto = teamMemberRepository.update(id, updatedDto);

        if (retrievedDto == null) {
            return CustomException.throwCustomException(Response.Status.OK, "A Team Member with id " + id + " was not found.");
        }

        if (retrievedDto.getTeamId() == null) {

            return Response.status(Response.Status.OK).entity(Arrays.asList(retrievedDto, "The Team Id: " + savedId + ", that you entered is not valid. Other changes if any were saved.")).build();
        }

        return Response.status(Response.Status.OK).entity(retrievedDto).build();

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeamMember(@PathParam("id") Long id) {
        TeamMemberDTO teamMemberDTO = teamMemberRepository.delete(id);

        if (teamMemberDTO == null) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Team Member with id: " + id + " not found");
        }

        return CustomException.throwCustomException(Response.Status.OK, "Team Member with id: " + id + " deleted");


    }


}
