package my.learn.paymentservice.service;

import my.learn.paymentservice.model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> findAllPayments();
}
