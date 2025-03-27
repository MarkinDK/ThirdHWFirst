package my.learn.paymentservice.service;

import jakarta.annotation.PostConstruct;
import my.learn.basedomain.model.order.OrderInfo;
import my.learn.basedomain.model.payment.PaymentStatus;
import my.learn.paymentservice.kafka.PaymentEventProducer;
import my.learn.paymentservice.model.Account;
import my.learn.paymentservice.model.Payment;
import my.learn.paymentservice.repository.AccountRepository;
import my.learn.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentEventProducer paymentEventProducer;

    private UUID accountId;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            AccountRepository accountRepository,
            PaymentEventProducer paymentEventProducer
    ) {
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
        this.paymentEventProducer = paymentEventProducer;
    }

    @PostConstruct
    private void init() {
        Account accountToSave = new Account();
        accountToSave.setAmount(10000.0);
        Account account = accountRepository.save(accountToSave);
        accountId = account.getId();
    }

    @Override
    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }


    @Transactional
    @Override
    public Payment payForOrder(OrderInfo orderInfo) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        Account account = optionalAccount.orElse(new Account(UUID.randomUUID(), 10000.0));
        Payment paymentToSave = new Payment();
        paymentToSave.setPrice(orderInfo.getPrice());
        paymentToSave.setOrderId(orderInfo.getOrderId());
        Payment savedPayment;
        if (account.getAmount() >= orderInfo.getPrice()) {
            account.setAmount(account.getAmount() - orderInfo.getPrice());
            accountRepository.save(account);

            paymentToSave.setStatus(PaymentStatus.APPROVED);
            savedPayment = paymentRepository.save(paymentToSave);

            paymentEventProducer.producePaymentApprovedEvent(savedPayment);
        } else {
            paymentToSave.setStatus(PaymentStatus.REJECTED);
            savedPayment = paymentRepository.save(paymentToSave);

            paymentEventProducer.producePaymentRejectedEvent(savedPayment);
        }
        return savedPayment;
    }
}
