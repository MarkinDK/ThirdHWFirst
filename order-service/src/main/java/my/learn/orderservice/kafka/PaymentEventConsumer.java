package my.learn.orderservice.kafka;

import my.learn.basedomain.event.PaymentEvent;
import my.learn.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {
    private final OrderService orderService;

    public PaymentEventConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics =
            {"${kafka.topic.name.payment.approved}", "${kafka.topic.name.payment.rejected}"},
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumePaymentEvent(PaymentEvent paymentEvent) {

        orderService.updateOrderAfterPayment(paymentEvent.getPaymentInfo(), paymentEvent.getStatus());
    }
}
