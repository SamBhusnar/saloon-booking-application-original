package com.mycompany.payment_service.controller;

import com.mycompany.payment_service.domain.PaymentMethod;
import com.mycompany.payment_service.dto.BookingDto;
import com.mycompany.payment_service.dto.PaymentLinkResponse;
import com.mycompany.payment_service.dto.UserDto;
import com.mycompany.payment_service.model.PaymentOrder;
import com.mycompany.payment_service.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLinkResponse(
            @RequestBody BookingDto bookingDto,
            @RequestParam PaymentMethod paymentMethod
            ) throws StripeException, RazorpayException {

        // simulate userdto
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFullName("Vaibhav Bhusnar");
        userDto.setEmail("john.doe@example.com");

        return
                ResponseEntity
                .ok(paymentService
                        .createOrder(userDto,bookingDto, paymentMethod));

    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId

    ) throws StripeException, RazorpayException {



        return
                ResponseEntity
                        .ok(paymentService
                                .getPaymentOrderById(paymentOrderId));


    }

    




}
