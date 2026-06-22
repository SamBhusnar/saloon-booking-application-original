package com.mycompany.payment_service.dto;


import lombok.Data;

@Data
public class PaymentLinkResponse {
    private String  urlLink;
    private String paymentLinkId;

}
