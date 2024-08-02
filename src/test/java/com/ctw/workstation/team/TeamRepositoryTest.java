package com.ctw.workstation.team;

import com.ctw.workstation.shared.exceptions.CustomException;
import com.ctw.workstation.team.boundary.TeamResource;
import com.ctw.workstation.team.control.TeamRepository;
import com.ctw.workstation.team.entity.dto.TeamDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
public class TeamRepositoryTest {
    @InjectMock
    TeamRepository teamRepository;

    @Inject
    TeamResource teamResource;

    TeamDTO mockTeam;

    @BeforeEach
    public void setUp() {
        mockTeam = new TeamDTO();
        mockTeam.setName("test");
        mockTeam.setProduct("test");
        mockTeam.setDefaultLocation("test");
        mockTeam.setId(1L); // Ensure ID is set if necessary
    }

    @Test
    public void addTeam() {
        Mockito.when(teamRepository.save(mockTeam)).thenReturn(mockTeam);


        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setDefaultLocation("test");
        teamDTO.setName("test");
        teamDTO.setProduct("test");
        teamDTO.setId(1L);

        try {
            Response response = teamResource.addTeam(teamDTO);

            TeamDTO retrievedDto = (TeamDTO) response.getEntity();

            Assertions.assertEquals(200, response.getStatus());
            Assertions.assertEquals("test", retrievedDto.getName());
            Assertions.assertEquals("test", retrievedDto.getDefaultLocation());
            Assertions.assertEquals("test", retrievedDto.getProduct());
            Assertions.assertEquals(1L, retrievedDto.getId());
        } catch (CustomException e) {
            Assertions.fail("CustomException was thrown: " + e.getMessage());
        }
    }
}