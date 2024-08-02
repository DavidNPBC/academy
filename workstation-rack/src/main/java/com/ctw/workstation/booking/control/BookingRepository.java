package com.ctw.workstation.booking.control;

import com.ctw.workstation.booking.entity.dto.BookingDTO;
import com.ctw.workstation.booking.entity.mappers.MapperBooking;
import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.entity.TeamMember;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class BookingRepository implements PanacheRepository<Booking> {

    @Transactional
    public BookingDTO save(BookingDTO dto) {
        Rack rack = getEntityManager().find(Rack.class, dto.getRackId());
        TeamMember member = getEntityManager().find(TeamMember.class, dto.getRequesterId());


        Booking booking = MapperBooking.dtoToBooking(dto);

        if (rack == null && member == null) {

            dto.setRequesterId(null);
            dto.setRackId(null);

            persist(booking);
            dto.setId(booking.getId());

            return dto;
        } else if (rack != null && member != null) {

            rack.addBooking(booking);
            member.addBooking(booking);

            persist(booking);

            dto.setId(booking.getId());

            return dto;
        } else if (rack == null) {
            dto.setRackId(null);
            member.addBooking(booking);
            persist(booking);

            dto.setId(booking.getId());
            return dto;
        } else {
            dto.setRequesterId(null);
            rack.addBooking(booking);

            persist(booking);

            dto.setId(booking.getId());
            return dto;
        }
    }

    @Transactional
    public BookingDTO getById(Long id) {

        Booking booking = findById(id);

        if (booking == null) {
            return null;
        }

        BookingDTO dto = MapperBooking.bookingToDto(booking);

        return dto;
    }

    @Transactional
    public List<BookingDTO> listAllBookings() {

        List<Booking> bookingList = listAll();

        if (bookingList.isEmpty()) {
            return new ArrayList<>();
        }

        List<BookingDTO> dtoList = bookingList.stream().map(booking -> MapperBooking.bookingToDto(booking)).toList();

        return dtoList;
    }

    @Transactional
    public BookingDTO update(Long id, BookingDTO updatedDto) {
        Booking existingBooking = findById(id);

        Rack rack = getEntityManager().find(Rack.class, updatedDto.getRackId());
        TeamMember member = getEntityManager().find(TeamMember.class, updatedDto.getRequesterId());

        // new member exists and the object already has a member
        if (member != null && existingBooking.getRequester() != null) {
            TeamMember existingTeamMember = existingBooking.getRequester();
            existingTeamMember.removeBooking(existingBooking);
            member.addBooking(existingBooking);
        }

        // new member exists and object had no prior member
        if (member != null && existingBooking.getRequester() == null) {
            member.addBooking(existingBooking);
        }

        // new member exists and object had a prior member
        if (member == null && existingBooking.getRequester() != null) {
            existingBooking.getRequester().removeBooking(existingBooking);
        }

        // new rack exists and the object already has a rack
        if (rack != null && existingBooking.getRack() != null) {
            Rack existingRack = existingBooking.getRack();
            existingRack.removeBooking(existingBooking);
            rack.addBooking(existingBooking);
        }

        // new rack exists and object had no prior rack
        if (rack != null && existingBooking.getRack() == null) {
            rack.addBooking(existingBooking);
        }

        // new rack exists and object had a prior rack
        if (rack == null && existingBooking.getRack() != null) {
            existingBooking.getRack().removeBooking(existingBooking);
        }

        Booking updatedBooking = MapperBooking.updateBooking(existingBooking, updatedDto);

        persist(updatedBooking);
        updatedDto.setId(id);
        return updatedDto;
    }

    @Transactional
    public BookingDTO delete(Long id) {
        Booking booking = findById(id);
        if (booking == null) {
            return null;
        }
        deleteById(id);

        return MapperBooking.bookingToDto(booking);
    }
}
