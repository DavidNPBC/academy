package com.ctw.workstation.team;

import com.ctw.workstation.team.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TeamTest {
    @Test
    void testId() {
        //test setup
        Team team = new Team();
        team.setId(1L);

        //when
        Long result = team.getId();

        //then
        Assertions.assertEquals(result, 1, "validate if attributed Id is correct");

    }

    @Test
    void testTeamName(){
        Team mockitoTeam = Mockito.mock(Team.class);
        mockitoTeam.setName("ze");

        Mockito.when(mockitoTeam.getName()).thenReturn("ze");

        Assertions.assertEquals("ze", mockitoTeam.getName(), "validate if attributed name is correct");
    }


}
