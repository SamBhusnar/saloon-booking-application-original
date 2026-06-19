package com.mycompany.booking_service.model;

import lombok.Data;

@Data
public class SalonReport {

    private Long salonId;
    private String salonName;
    private Integer totalBookings;
    private Double totalEarnings;
    private Integer cancelledBookings;
    private Double totalRefundAmount;


}
