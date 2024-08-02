package com.ctw.workstation.booking.entity.mappers;

import com.ctw.workstation.booking.entity.Booking;
import com.ctw.workstation.booking.entity.dto.BookingDTO;
import com.ctw.workstation.teammember.entity.TeamMember;
import com.ctw.workstation.teammember.entity.dto.TeamMemberDTO;

public class MapperBooking {
    public static Booking dtoToBooking(BookingDTO dto){
        Booking booking = new Booking();
        booking.setBookFrom(dto.getBookFrom());
        booking.setBookTo(dto.getBookTo());

        return booking;
    }

    public static BookingDTO bookingToDto(Booking booking){
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setBookFrom(booking.getBookFrom());
        dto.setBookTo(booking.getBookTo());

        if(booking.getRack() == null) {
            dto.setRackId(null);
        } else {
            dto.setRackId(booking.getRack().getId());
        }

        if(booking.getRequester() == null) {
            dto.setRequesterId(null);
        } else {
            dto.setRequesterId(booking.getRequester().getId());
        }
        return dto;
    }

    public static Booking updateBooking(Booking booking, BookingDTO dto){
        booking.setBookFrom(dto.getBookFrom());
        booking.setBookTo(dto.getBookTo());
        if(booking.getRack() == null) {
            dto.setRackId(null);
        } else {
            dto.setRackId(booking.getRack().getId());
        }

        if(booking.getRequester() == null) {
            dto.setRequesterId(null);
        } else {
            dto.setRequesterId(booking.getRequester().getId());
        }
        return booking;
    }
}
