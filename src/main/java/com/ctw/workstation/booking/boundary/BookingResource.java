package com.ctw.workstation.booking.boundary;

import com.ctw.workstation.booking.control.BookingRepository;
import com.ctw.workstation.booking.entity.dto.BookingDTO;
import com.ctw.workstation.shared.exceptions.CustomException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;


@Path("/booking")
public class BookingResource {

    @Inject
    private BookingRepository bookingRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(BookingDTO bookingDTO) {
        if (bookingDTO.getBookTo() == null || bookingDTO.getBookFrom() == null) {

            String missing = bookingDTO.getBookTo() == null ? "Book To" : "Book From";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Missing information. " + missing + " is missing");
        }

        BookingDTO dto = bookingRepository.save(bookingDTO);

        if (dto.getRackId() == null && dto.getRequesterId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(dto, "Created a Booking without a Rack nor a Team Member/Requester")).build();
        }

        if (dto.getRackId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(dto, "Created a Booking without a Rack")).build();
        }

        if (dto.getRequesterId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(dto, "Created a Booking without a Requester/Team Member")).build();
        }

        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingsById(@PathParam("id") Long id) {
        BookingDTO bookingDTO = bookingRepository.getById(id);

        if (bookingDTO == null) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "Invalid Booking id: " + id);
        }

        return Response.status(Response.Status.OK).entity(bookingDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings() {
        List<BookingDTO> bookingDTOList = bookingRepository.listAllBookings();

        if (bookingDTOList.isEmpty()) {
            return CustomException.throwCustomException(Response.Status.NOT_FOUND, "There are no Bookings in the database.");
        }

        return Response.status(Response.Status.OK).entity(bookingDTOList).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") Long id, BookingDTO updatedBooking) {

        Long savedRackId = updatedBooking.getRackId();
        Long savedRequesterId = updatedBooking.getRequesterId();

        if (updatedBooking.getBookTo() == null || updatedBooking.getBookFrom() == null) {

            String invalid = updatedBooking.getBookTo() == null ? "Book To" : "Book From";

            return CustomException.throwCustomException(Response.Status.BAD_REQUEST, "Invalid data: " + invalid + " is invalid");
        }

        BookingDTO retrievedDto = bookingRepository.update(id, updatedBooking);

        if (retrievedDto.getRackId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(retrievedDto, "The Rack Id: " + savedRackId + ", that you entered is not valid. Other changes if any were saved.")).build();
        }

        if (retrievedDto.getRequesterId() == null) {
            return Response.status(Response.Status.OK).entity(Arrays.asList(retrievedDto, "The Requester/Team Member Id: " + savedRequesterId + ", that you entered is not valid. Other changes if any were saved.")).build();
        }

        return Response.status(Response.Status.OK).entity(retrievedDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") Long id) {
        BookingDTO bookingDTO = bookingRepository.delete(id);

        if (bookingDTO == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Booking with id: " + id + " not found").build();
        }

        return Response.status(Response.Status.OK).entity("Booking with id: " + id + " deleted").build();

    }

}
