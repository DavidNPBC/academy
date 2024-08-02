package com.ctw.workstation.rack;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.team.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RackTest {

    @Test
    void testAddTeamAndTeamName(){
        Team mockitoTeam = Mockito.mock(Team.class);
        mockitoTeam.setName("ze");

        Rack rack = Mockito.mock(Rack.class);
        Mockito.when(rack.getTeam()).thenReturn(mockitoTeam);

        Mockito.when(mockitoTeam.getName()).thenReturn("ze");

        Assertions.assertEquals("ze", rack.getTeam().getName() , "validate if attributed team name is correct");
    }


}
