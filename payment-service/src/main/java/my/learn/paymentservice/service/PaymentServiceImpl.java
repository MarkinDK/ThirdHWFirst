package my.learn.paymentservice.service;

import jakarta.annotation.PostConstruct;
import my.learn.paymentservice.model.Payment;
import my.learn.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostConstruct
    private void init() {
        paymentRepository.saveAll(List.of(
                new Payment(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID()),
                new Payment(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID()),
                new Payment(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID()),
                new Payment(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID())
        ));
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }
}
