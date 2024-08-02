package com.ctw.workstation.teammember.entity;


import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.shared.attributes.SharedAttributes;
import com.ctw.workstation.team.entity.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "T_TEAM_MEMBER")
public class TeamMember extends SharedAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberSequence")
    @SequenceGenerator(name = "memberSequence", sequenceName = "member_sq")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "ctw_id", nullable = false)
    private String ctwId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "requester", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Booking> bookings;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setCtwId(String ctwId) {
        this.ctwId = ctwId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public String getCtwId() {
        return ctwId;
    }

    public String getName() {
        return name;
    }

    public void addBooking (Booking booking) {
        bookings.add(booking);
        booking.setRequester(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setRequester(null);
    }

    public synchronized void clearRacks () {
        List<Booking> bookingsToRemove = new ArrayList<>(bookings);
        for (Booking booking : bookingsToRemove) {
            removeBooking(booking);
        }
    }
}
