package com.mycompany.booking_service.controller;

import com.mycompany.booking_service.domain.BookingStatus;
import com.mycompany.booking_service.dto.*;
import com.mycompany.booking_service.mapper.BookingMapper;
import com.mycompany.booking_service.model.Booking;
import com.mycompany.booking_service.model.SalonReport;
import com.mycompany.booking_service.service.BookingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/booking")
public class BookingController {


    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @NonNull @RequestParam Long salonId,
            @NonNull @RequestBody BookingRequest bookingRequest
    ) {
        // userdto
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        // salondto
        SalonDto salonDto = new SalonDto();
        salonDto.setId(salonId);

        Set<ServiceOfferingDto> serviceOfferingDto = new HashSet<>();
        ServiceOfferingDto dto = new ServiceOfferingDto();
        dto.setId(1L);
        dto.setPrice(399);
        dto.setDuration(45);
        dto.setName("Haircut");

        serviceOfferingDto.add(dto);

        Booking booking = bookingService
                .createBooking(
                        bookingRequest,
                        userDto,
                        salonDto,
                        serviceOfferingDto
                );


        return ResponseEntity.ok(booking);


    }

    // get booking by customer
    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDto>> getBookingsByCustomerId(
    ) {
        List<Booking> bookings = bookingService.getBookingsByCustomerId(1L);




        return  ResponseEntity.ok(toBookingDtoSet(bookings));

    }
    private Set<BookingDto> toBookingDtoSet(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toSet());
    }


    // get booking by customer
    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDto>> getBookingsBySalonId(
    ) {
        List<Booking> bookings = bookingService.getBookingsBySalonId(1L);




        return  ResponseEntity.ok(toBookingDtoSet(bookings));

    }

    // get booking by  id
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingsById(
            @PathVariable Long bookingId
    ) {
        Booking bookings = bookingService.getBookingById
                (bookingId);




            return  ResponseEntity.ok(BookingMapper.toBookingDto(bookings));

    }



    // update booking status
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDto> getBookingsById(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) {
        Booking booking = bookingService.updateBooking(bookingId,status);





        return  ResponseEntity.ok(BookingMapper.toBookingDto(booking));

    }

    // update booking status
    @PutMapping("/slot/salon/{salonId}/date/{date}")
    public ResponseEntity<List<SlotDto>> getBookSlot(
            @PathVariable Long salonId,
            @PathVariable(required = false) LocalDate date
    ) {
        List<Booking> bookings = bookingService.getBookingByDate( date,salonId);

        List<SlotDto> slots = bookings.stream()
                .map(BookingMapper::toSlotDto)
                .toList();



        return  ResponseEntity.ok(

               slots

        );

    }

    // get booking report
    @PutMapping("/report")
    public ResponseEntity<SalonReport> getBookingReport(
    ) {
       SalonReport reportSalon = bookingService.getSalonReport(1L);

       return ResponseEntity.ok(reportSalon);

    }

}
