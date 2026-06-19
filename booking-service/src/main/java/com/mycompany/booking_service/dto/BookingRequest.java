package com.mycompany.booking_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingRequest {

    private Long salonId;


    private LocalDateTime startTime;


    private LocalDateTime endTime;

    private Set<Long> serviceIds;


}
