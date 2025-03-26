package my.learn.orderservice.kafka;

import my.learn.basedomain.event.OrderEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderEventProducer {
    @Qualifier("orderCreatedTopic")
    private final NewTopic orderCreatedTopic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(NewTopic orderCreatedTopic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.orderCreatedTopic = orderCreatedTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, OrderEvent>> produceOrderCreatedEvent(OrderEvent orderEvent) {
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, orderCreatedTopic.name())
                .build();
        return kafkaTemplate.send(message);
    }
}
