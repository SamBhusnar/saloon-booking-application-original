package com.mycompany.booking_service.dto;

import com.mycompany.booking_service.domain.BookingStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {

    private Long id;

    private Long salonId;


    private Long customerId;

    private LocalDateTime startTime;


    private LocalDateTime endTime;


    
    private Set<Long> serviceIds;

    private BookingStatus status = BookingStatus.PENDING;


    private int totalPrice;

}
