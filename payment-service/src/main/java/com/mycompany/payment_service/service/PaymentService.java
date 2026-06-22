package com.mycompany.payment_service.service;

import com.mycompany.payment_service.domain.PaymentMethod;
import com.mycompany.payment_service.dto.BookingDto;
import com.mycompany.payment_service.dto.PaymentLinkResponse;
import com.mycompany.payment_service.dto.UserDto;
import com.mycompany.payment_service.model.PaymentOrder;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentLinkResponse createOrder(
                                    UserDto userDto,
                                    BookingDto bookingDto,
                                    PaymentMethod paymentMethod
                                    ) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(
            UserDto userDto,
            Long amount,
            Long orderId
    ) throws RazorpayException;

    String createStripePaymentLink(
            UserDto userDto,
            Long amount,
            Long orderId
    ) throws StripeException;

    Boolean proceedPayment(
          PaymentOrder paymentOrder,
          String paymentId,
          String paymentLinkId
    ) throws StripeException, RazorpayException;
}
