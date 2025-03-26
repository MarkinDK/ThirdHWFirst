package my.learn.paymentservice.kafka;

import my.learn.basedomain.event.PaymentEvent;
import my.learn.basedomain.model.payment.PaymentInfo;
import my.learn.paymentservice.model.Payment;
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
public class PaymentEventProducer {
    @Qualifier("paymentApprovedTopic")
    private final NewTopic paymentApprovedTopic;

    @Qualifier("paymentRejectedTopic")
    private final NewTopic paymentRejectedTopic;

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducer(NewTopic paymentApprovedTopic, NewTopic paymentRejectedTopic, KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.paymentApprovedTopic = paymentApprovedTopic;
        this.paymentRejectedTopic = paymentRejectedTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, PaymentEvent>> producePaymentApprovedEvent(Payment approvedPayment) {
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setPaymentInfo(
                new PaymentInfo(approvedPayment.getOrderId(), approvedPayment.getPaymentId(), approvedPayment.getPrice())
        );

        Message<PaymentEvent> message = MessageBuilder
                .withPayload(paymentEvent)
                .setHeader(KafkaHeaders.TOPIC, paymentApprovedTopic.name())
                .build();
        return kafkaTemplate.send(message);
    }

    public CompletableFuture<SendResult<String, PaymentEvent>> producePaymentRejectedEvent(Payment rejectedPayment) {
        PaymentEvent paymentEvent = new PaymentEvent();
        paymentEvent.setPaymentInfo(
                new PaymentInfo(rejectedPayment.getOrderId(), rejectedPayment.getPaymentId(), rejectedPayment.getPrice())
        );

        Message<PaymentEvent> message = MessageBuilder
                .withPayload(paymentEvent)
                .setHeader(KafkaHeaders.TOPIC, paymentRejectedTopic.name())
                .build();
        return kafkaTemplate.send(message);
    }
}
