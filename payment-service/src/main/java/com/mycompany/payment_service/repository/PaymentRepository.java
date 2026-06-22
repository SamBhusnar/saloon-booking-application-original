package com.mycompany.payment_service.repository;

import com.mycompany.payment_service.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentOrder, Long> {
    Optional<PaymentOrder> findByPaymentLinkId(String paymentLinkId);
}
