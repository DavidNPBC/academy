package com.ctw.workstation.teammember.unittest;

import com.ctw.workstation.team.control.TeamRepository;
import com.ctw.workstation.team.entity.dto.TeamDTO;
import io.quarkus.test.junit.QuarkusTest;

import com.ctw.workstation.teammember.boundary.TeamMemberResource;
import com.ctw.workstation.teammember.control.TeamMemberRepository;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class TeamMemberResourceTest {

    @InjectMock
    TeamMemberRepository teamMemberRepository;

    @Inject
    TeamMemberResource teamMemberResource;

    @InjectMock
    TeamRepository teamRepository;

    TeamMemberDTO mockDto;

    TeamDTO mockTeam;

    @BeforeEach
    public void setUp() {
        mockDto = new TeamMemberDTO();
        mockDto.setName("test");
        mockDto.setTeamId(1L);
        mockDto.setCtwId("ctw1");
        mockDto.setId(1L);
    }

    @Test
    public void addTeamMember() {

        mockTeam = new TeamDTO();
        mockTeam.setDefaultLocation("fdfs");
        mockTeam.setName("daf");
        mockTeam.setProduct("f");

        //Mockito.when(teamMemberRepository.getEntityManager().find(Team.class, mockDto.getTeamId())).thenReturn(mockTeam);
        Mockito.when(teamRepository.save(mockTeam)).thenReturn(mockTeam);

        Mockito.when(teamMemberRepository.save(mockDto))
                .thenReturn(mockDto);

        TeamMemberDTO dto = new TeamMemberDTO();
        dto.setName("test");
        dto.setCtwId("ctw1");
        dto.setTeamId(1L);

        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setDefaultLocation("fdfs");
        teamDTO.setName("daf");
        teamDTO.setProduct("f");

        TeamDTO teamResponse = teamRepository.save(teamDTO);

        Response response = teamMemberResource.addTeamMember(dto);


        TeamMemberDTO retrievedDto = (TeamMemberDTO) response.getEntity();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("test", retrievedDto.getName());
        Assertions.assertEquals("ctw1", retrievedDto.getCtwId());
        Assertions.assertEquals(1L, retrievedDto.getTeamId());
        Assertions.assertEquals(1L, retrievedDto.getId());
    }

    @Test
    public void getTeamMembers() {
        List<TeamMemberDTO> list = new ArrayList<>();
        list.add(mockDto);

        Mockito.when(teamMemberRepository.listAllTeamMembers())
                .thenReturn(list);

        Response response = teamMemberResource.getTeamMembers();

        list = (List<TeamMemberDTO>) response.getEntity();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("test", list.get(0).getName());
        Assertions.assertEquals("ctw1", list.get(0).getCtwId());
        Assertions.assertEquals(1L, list.get(0).getTeamId());
        Assertions.assertEquals(1L, list.get(0).getId());
    }

    @Test
    public void get() {
        given().when().get("/workstation/team-members").then().statusCode(200).body(is("[]"));
    }

    @Test
    public void getTeamMembersById() {
        Mockito.when(teamMemberRepository.getById(1L)).thenReturn(mockDto);

        Response response = teamMemberResource.getTeamMembersById(1L);

        TeamMemberDTO retrievedDto = (TeamMemberDTO) response.getEntity();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("test", retrievedDto.getName());
        Assertions.assertEquals("ctw1", retrievedDto.getCtwId());
        Assertions.assertEquals(1L, retrievedDto.getTeamId());
        Assertions.assertEquals(1L, retrievedDto.getId());

    }

    @Test
    public void getTeamMembersByWrongId() {
        Mockito.when(teamMemberRepository.getById(2L)).thenReturn(null);

        try {
            Response response = teamMemberResource.getTeamMembersById(2L);
        } catch (Exception e) {
            Assertions.assertEquals("Invalid Team Member id: 2", e.getMessage());
        }

    }

    @Test
    public void updateTeamMember() {
        // Implement test logic here
    }

    @Test
    public void deleteTeamMember() {
        // Implement test logic here
    }
}
