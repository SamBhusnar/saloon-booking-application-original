package com.mycompany.booking_service.service.impl;

import com.mycompany.booking_service.domain.BookingStatus;
import com.mycompany.booking_service.dto.BookingRequest;
import com.mycompany.booking_service.dto.SalonDto;
import com.mycompany.booking_service.dto.ServiceOfferingDto;
import com.mycompany.booking_service.dto.UserDto;
import com.mycompany.booking_service.model.Booking;
import com.mycompany.booking_service.model.SalonReport;
import com.mycompany.booking_service.repository.BookingRepository;
import com.mycompany.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(@NonNull BookingRequest booking,
                                 @NonNull UserDto userDto,
                                 @NonNull SalonDto salonDto,
                                 @NonNull Set<ServiceOfferingDto> serviceOfferingDto
    ) {

        int totalDuration = serviceOfferingDto.stream()
                .mapToInt(ServiceOfferingDto::getDuration).sum();

        LocalDateTime bookingStart = booking.getStartTime();
        LocalDateTime bookingEnd = bookingStart.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeSlotAvailable(salonDto, bookingStart, bookingEnd);

        int totalPrice = serviceOfferingDto.stream()
                .mapToInt(ServiceOfferingDto::getPrice).sum();
        Set<Long> idList = serviceOfferingDto.stream()
                .map(ServiceOfferingDto::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(userDto.getId());
        newBooking.setSalonId(salonDto.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStart);
        newBooking.setEndTime(bookingEnd);
        newBooking.setTotalPrice(totalPrice);


        return bookingRepository.save(newBooking);
    }


    public Boolean isTimeSlotAvailable(
            @NonNull SalonDto salonDto,
            @NonNull LocalDateTime startTime,
            @NonNull LocalDateTime endTime

    ) {


        LocalDateTime salonOpenTime = salonDto.getOpenTime().atDate(startTime.toLocalDate());

        LocalDateTime salonCloseTime = salonDto.getCloseTime().atDate(endTime.toLocalDate());

        // check if booking time is within salon working hours
        if (startTime.isBefore(salonOpenTime) || endTime.isAfter(salonCloseTime)) {
            throw new RuntimeException("Booking time must be within salon working hours");

        }
        // check booking time is overlapping with existing booking
        List<Booking> existingBookings = getBookingsBySalonId(salonDto.getId());
        for (Booking booking : existingBookings) {
            if (startTime.isBefore(booking.getEndTime())
                    &&
                    endTime.isAfter(booking.getStartTime())) {
                throw new RuntimeException("slot is not available , choose another slot");
            }
            // check start time and end time is equals to the existing booking start and end time
            if (startTime.isEqual(booking.getStartTime())
                    &&
                    endTime.isEqual(booking.getEndTime())) {
                throw new RuntimeException("slot is not available , choose another slot");
            }
        }


        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalonId(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public Booking updateBooking(Long id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);


        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByDate(LocalDate date, Long salonId) {

        List<Booking> allBooking = getBookingsBySalonId(salonId);
        if (date == null) {
            return allBooking;
        }
        return allBooking.stream()
                .filter(booking ->
                        isSameDate(booking.getStartTime(), date)
                                ||
                                isSameDate(booking.getEndTime(), date))
                .collect(Collectors.toList());


    }

    private boolean isSameDate(@NonNull LocalDateTime dateTime, LocalDate date) {
        return dateTime.toLocalDate().equals(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {

        List<Booking> allBooking = getBookingsBySalonId(salonId);
        Double totalEarnings = allBooking.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();
        Integer totalBookings = allBooking.size();

        // canceled booking
        List<Booking> cancelledBookings = allBooking.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .toList();
        // calculate total refund amount
        Double totalRefundAmount = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();
        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setSalonName("Kartik hair salon stdio");
        salonReport.setTotalBookings(totalBookings);
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setCancelledBookings(cancelledBookings.size());
        salonReport.setTotalRefundAmount(totalRefundAmount);

        return salonReport;
    }
}
