package com.ctw.workstation.team.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.shared.attributes.SharedAttributes;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "T_TEAM")
public class Team extends SharedAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSequence")
    @SequenceGenerator(name = "teamSequence", sequenceName = "team_sq")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String product;

    @Column(name = "default_location", nullable = false)
    private String defaultLocation;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TeamMember> teamMembers;

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Rack> racks;

    public String getName() {
        return name;
    }

    public String getProduct() {
        return product;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    public List<Rack> getRacks() {
        return racks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public void setRacks(List<Rack> racks) {
        this.racks = racks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void addTeamMember(TeamMember teamMember) {
        teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }

    public void removeTeamMember(TeamMember teamMember) {
        teamMembers.remove(teamMember);
        teamMember.setTeam(null);
    }

    public synchronized void clearTeamMembers () {
        List<TeamMember> membersToRemove = new ArrayList<>(teamMembers);
        for (TeamMember member : membersToRemove) {
            removeTeamMember(member);
        }
    }

    public void addRack(Rack rack) {
        racks.add(rack);
        rack.setTeam(this);
    }

    public void removeRack(Rack rack) {
        racks.remove(rack);
        rack.setTeam(null);
    }

    public synchronized void clearRacks () {
        List<Rack> racksToRemove = new ArrayList<>(racks);
        for (Rack rack : racksToRemove) {
            removeRack(rack);
        }
    }

}
