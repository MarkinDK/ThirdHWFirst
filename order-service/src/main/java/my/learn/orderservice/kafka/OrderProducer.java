package my.learn.orderservice.kafka;

import my.learn.basedomain.order.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    @Qualifier("orderCreatedTopic")
    private final NewTopic orderCreateTopic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(NewTopic orderCreateTopic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.orderCreateTopic = orderCreateTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, orderCreateTopic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
