package com.mycompany.booking_service.mapper;

import com.mycompany.booking_service.dto.BookingDto;
import com.mycompany.booking_service.dto.SlotDto;
import com.mycompany.booking_service.model.Booking;

public class BookingMapper {
    public static BookingDto toBookingDto(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStatus(booking.getStatus());
        bookingDto.setTotalPrice(booking.getTotalPrice());
        bookingDto.setCustomerId(booking.getCustomerId());
        bookingDto.setStartTime(booking.getStartTime());
        bookingDto.setEndTime(booking.getEndTime());
        bookingDto.setSalonId(booking.getSalonId());
        bookingDto.setServiceIds(booking.getServiceIds());









        return bookingDto;
    }

    public static SlotDto toSlotDto(Booking booking) {
        SlotDto slotDto = new SlotDto();
        slotDto.setStartTime(booking.getStartTime());
        slotDto.setEndTime(booking.getEndTime());
        return slotDto;
    }
}
