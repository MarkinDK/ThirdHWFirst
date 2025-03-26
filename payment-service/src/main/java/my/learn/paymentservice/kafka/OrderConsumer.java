package my.learn.paymentservice.kafka;

import my.learn.basedomain.event.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    @KafkaListener(topics =
            "${kafka.topic.name.order.created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrder(OrderEvent orderEvent) {
        System.out.println(orderEvent);
    }
}
