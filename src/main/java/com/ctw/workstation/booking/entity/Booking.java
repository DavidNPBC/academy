package com.ctw.workstation.booking.entity;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.shared.attributes.SharedAttributes;
import com.ctw.workstation.teammember.entity.TeamMember;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_BOOKING")
public class Booking extends SharedAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSequence")
    @SequenceGenerator(name = "bookingSequence", sequenceName = "booking_sq")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "rack_id")
    private Rack rack;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private TeamMember requester;

    @Column(name = "book_from", nullable = false)
    private LocalDateTime bookFrom;

    @Column(name = "book_to", nullable = false)
    private LocalDateTime bookTo;

    public void setId(Long id) {
        this.id = id;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public void setRequester(TeamMember requester) {
        this.requester = requester;
    }

    public void setBookFrom(LocalDateTime bookFrom) {
        this.bookFrom = bookFrom;
    }

    public void setBookTo(LocalDateTime bookTo) {
        this.bookTo = bookTo;
    }

    public Long getId() {
        return id;
    }

    public Rack getRack() {
        return rack;
    }

    public TeamMember getRequester() {
        return requester;
    }

    public LocalDateTime getBookFrom() {
        return bookFrom;
    }

    public LocalDateTime getBookTo() {
        return bookTo;
    }
}
