package my.learn.orderservice.kafka;

import my.learn.basedomain.event.OrderEvent;
import my.learn.basedomain.model.order.OrderInfo;
import my.learn.basedomain.model.order.OrderStatus;
import my.learn.orderservice.model.OrderEntity;
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
    @Qualifier("orderPaidTopic")
    private final NewTopic orderPaidTopic;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(NewTopic orderCreatedTopic, NewTopic orderPaidTopic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.orderCreatedTopic = orderCreatedTopic;
        this.orderPaidTopic = orderPaidTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, OrderEvent>> produceOrderCreatedEvent(OrderEntity order) {
        OrderEvent orderEvent = constructOrderEvent(order);
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, orderCreatedTopic.name())
                .build();
        return kafkaTemplate.send(message);
    }

    public CompletableFuture<SendResult<String, OrderEvent>> produceOrderPaidEvent(OrderEntity order) {
        OrderEvent orderEvent = constructOrderEvent(order);
        Message<OrderEvent> message = MessageBuilder
                .withPayload(orderEvent)
                .setHeader(KafkaHeaders.TOPIC, orderPaidTopic.name())
                .build();
        return kafkaTemplate.send(message);
    }

    private static OrderEvent constructOrderEvent(OrderEntity order) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderInfo(new OrderInfo(order.getOrderId(), order.getPrice()));
        orderEvent.setStatus(order.getStatus());
        orderEvent.setMessage("\nOrder with id = " + order.getOrderId() + order.getStatus().toString() + "\n");
        return orderEvent;
    }
}
