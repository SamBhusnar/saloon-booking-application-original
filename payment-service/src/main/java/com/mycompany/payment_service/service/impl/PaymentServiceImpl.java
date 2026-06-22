package com.mycompany.payment_service.service.impl;

import com.mycompany.payment_service.domain.PaymentMethod;
import com.mycompany.payment_service.domain.PaymentOrderStatus;
import com.mycompany.payment_service.dto.BookingDto;
import com.mycompany.payment_service.dto.PaymentLinkResponse;
import com.mycompany.payment_service.dto.UserDto;
import com.mycompany.payment_service.model.PaymentOrder;
import com.mycompany.payment_service.repository.PaymentRepository;
import com.mycompany.payment_service.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private   PaymentRepository paymentRepository;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey ;


    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret ;


    @Value("${stripe.api.secret}")
    private String stripSecretKey ;

    @Override
    public PaymentLinkResponse createOrder(UserDto userDto, BookingDto bookingDto, PaymentMethod paymentMethod) throws RazorpayException, StripeException {

        Long amount = (long)bookingDto.getTotalPrice();

        PaymentOrder order=new PaymentOrder();
        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(bookingDto.getId());
        order.setSalonId(bookingDto.getSalonId().toString());
        PaymentOrder savedOrder = paymentRepository.save(order);
        PaymentLinkResponse paymentLinkResponse=new PaymentLinkResponse();



        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink payment=createRazorpayPaymentLink(userDto, savedOrder.getAmount(), savedOrder.getId());
            String paymentUrl=payment.get("short_url");
            String paymentUrlId=payment.get("id");
            paymentLinkResponse.setUrlLink(paymentUrl);

            paymentLinkResponse.setPaymentLinkId(paymentUrlId);
            savedOrder.setPaymentLinkId(paymentUrlId);
            paymentRepository.save(savedOrder);
        }else{

            String paymentUrl=createStripePaymentLink(userDto, savedOrder.getAmount(), savedOrder.getId());
            paymentLinkResponse.setUrlLink(paymentUrl);
            savedOrder.setPaymentLinkId(paymentUrl);
            paymentRepository.save(savedOrder);
        }



        return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {


        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {


        return paymentRepository.findByPaymentLinkId(paymentId).orElseThrow(() -> new RuntimeException("Payment order not found"));
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(
            UserDto userDto,
            Long myAmount,
            Long orderId) throws RazorpayException {
            Long amount=myAmount*100;// b'z in razor pay amount will be i razor pay

                RazorpayClient razorpayClient = new RazorpayClient(razorpayApiKey, razorpayApiSecret);
                JSONObject paymentLinkRequest = new JSONObject();
                paymentLinkRequest.put("amount", amount);
                paymentLinkRequest.put("currency", "INR");


                JSONObject customer = new JSONObject();
                customer.put("name", userDto.getFullName());
                customer.put("email", userDto.getEmail());

                paymentLinkRequest.put("customer", customer);


                JSONObject notify=new JSONObject();
                notify.put("sms", true);
                notify.put("email", true);


                paymentLinkRequest.put("notify",notify);
                // enable reminder
                paymentLinkRequest.put("reminder_enable", true);
                // put call back url
                paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/"+orderId);
                paymentLinkRequest.put("callback_method","get");

                return razorpayClient.paymentLink.create(paymentLinkRequest);




    }

    @Override
    public String createStripePaymentLink(UserDto userDto, Long amount, Long orderId) throws StripeException {

        Stripe.apiKey=stripSecretKey;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success"+orderId)
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams.LineItem.
                                        PriceData.ProductData.builder()
                                        .setName("salon_appointment_booking")
                                        .build()).build()

                        )

                        .build()).build();


        Session session= Session.create(params);

        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(
            PaymentOrder paymentOrder,
            String paymentId,
            String paymentLinkId) throws StripeException, RazorpayException {

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING))
        {
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY))
            {
                RazorpayClient razorpayClient=new RazorpayClient(razorpayApiKey, razorpayApiSecret);

                Payment payment=razorpayClient.payments.fetch(paymentId);
                Integer amount=payment.get("amount");
                String status=payment.get("status");
                if(status.equals("captured"))
                {
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentRepository.save(paymentOrder);
                    return true;
                }
                return false;

            }else {

                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentRepository.save(paymentOrder);
                return true;

            }

        }

        return false;
    }


}
