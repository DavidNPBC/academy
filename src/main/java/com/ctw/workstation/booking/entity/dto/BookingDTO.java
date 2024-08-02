package com.ctw.workstation.booking.entity.dto;

import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class BookingDTO {
    private Long id;
    @JsonProperty("rack_id")
    private Long rackId;
    @JsonProperty("requester_id")
    private Long requesterId;
    @JsonProperty("book_from")
    private LocalDateTime bookFrom;
    @JsonProperty("book_to")
    private LocalDateTime bookTo;

    public Long getRackId() {
        return rackId;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public LocalDateTime getBookFrom() {
        return bookFrom;
    }

    public LocalDateTime getBookTo() {
        return bookTo;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }

    public void setBookFrom(LocalDateTime bookFrom) {
        this.bookFrom = bookFrom;
    }

    public void setBookTo(LocalDateTime bookTo) {
        this.bookTo = bookTo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
