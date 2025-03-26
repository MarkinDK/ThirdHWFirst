package my.learn.paymentservice.kafka;

import my.learn.basedomain.event.OrderEvent;
import my.learn.paymentservice.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {
    private final PaymentService paymentService;

    public OrderEventConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics =
            "${kafka.topic.name.order.created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderCreatedEvent(OrderEvent orderEvent) {
        paymentService.payForOrder(orderEvent.getOrderInfo());
    }
}
