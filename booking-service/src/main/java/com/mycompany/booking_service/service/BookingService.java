package com.mycompany.booking_service.service;

import com.mycompany.booking_service.domain.BookingStatus;
import com.mycompany.booking_service.dto.BookingRequest;
import com.mycompany.booking_service.dto.SalonDto;
import com.mycompany.booking_service.dto.ServiceOfferingDto;
import com.mycompany.booking_service.dto.UserDto;
import com.mycompany.booking_service.model.Booking;
import com.mycompany.booking_service.model.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(
            BookingRequest booking,
            UserDto userDto,
            SalonDto salonDto,
            Set<ServiceOfferingDto> serviceOfferingDto
    );

    List<Booking> getBookingsByCustomerId(Long customerId);

    List<Booking> getBookingsBySalonId(Long salonId);

    Booking getBookingById(Long id);

    Booking updateBooking(Long id, BookingStatus status);

    List<Booking> getBookingByDate(LocalDate date, Long salonId);

    SalonReport getSalonReport(Long salonId);


}
