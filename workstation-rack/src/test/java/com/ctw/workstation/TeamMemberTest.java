package com.ctw.workstation;

import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.control.TeamMemberRepository;
import com.ctw.workstation.teammember.entity.TeamMember;
import io.quarkus.test.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

public class TeamMemberTest {

    @InjectMock
    TeamMemberRepository teamMemberRepository;

    @Test
    void testAddTeamMember() {
        Team team = Mockito.mock(Team.class);
        team.setName("Dev Team");
        TeamMember member = Mockito.mock(TeamMember.class);
        member.setName("Joe");
        member.setTeam(team);

        when(team.getName()).thenReturn("Dev Team");
        when(member.getTeam()).thenReturn(team);
        when(member.getTeam().getName()).thenReturn("Dev Team");


        Assertions.assertEquals("Dev Team", member.getTeam().getName(), "Should be the Dev Team");
    }

}
