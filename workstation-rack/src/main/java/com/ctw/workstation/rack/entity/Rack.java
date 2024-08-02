package com.ctw.workstation.rack.entity;

import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rackasset.entity.RackAsset;
import com.ctw.workstation.shared.attributes.SharedAttributes;
import com.ctw.workstation.team.entity.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "T_RACK")
public class Rack extends SharedAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rackSequence")
    @SequenceGenerator(name = "rackSequence", sequenceName = "rack_sq")
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "default_location", nullable = false)
    private String defaultLocation;

    @Column(nullable = false)
    private String status;


    @OneToMany(mappedBy = "rack", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<RackAsset> rackAssets;

    @OneToMany(mappedBy = "rack", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Booking> bookings;

    public void setId(Long id) {
        this.id = id;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setDefaultLocation(String defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRackAssets(Set<RackAsset> rackAssets) {
        this.rackAssets = rackAssets;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Long getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Team getTeam() {
        return team;
    }

    public String getDefaultLocation() {
        return defaultLocation;
    }

    public String getStatus() {
        return status;
    }

    public Set<RackAsset> getRackAssets() {
        return rackAssets;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void addRackAsset(RackAsset rackAsset) {
        rackAssets.add(rackAsset);
        rackAsset.setRack(this);
    }

    public void removeRackAsset(RackAsset rackAsset) {
        rackAssets.remove(rackAsset);
        rackAsset.setRack(null);
    }

    public synchronized void clearRackAssets () {
        List<RackAsset> rackAssetsToRemove = new ArrayList<>(rackAssets);
        for (RackAsset rackAsset : rackAssetsToRemove) {
            removeRackAsset(rackAsset);
        }
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setRack(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setRack(null);
    }

    public synchronized void clearBookings () {
        List<Booking> bookingsToRemove = new ArrayList<>(bookings);
        for (Booking booking : bookingsToRemove) {
            removeBooking(booking);
        }
    }
}
