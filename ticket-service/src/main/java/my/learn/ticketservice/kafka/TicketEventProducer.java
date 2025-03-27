package my.learn.ticketservice.kafka;

import my.learn.basedomain.event.TicketEvent;
import my.learn.basedomain.model.ticket.TicketInfo;
import my.learn.ticketservice.model.Ticket;
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
public class TicketEventProducer {
    @Qualifier("ticketApprovedTopic")
    private final NewTopic ticketApprovedTopic;

    @Qualifier("ticketCancelledTopic")
    private final NewTopic ticketCancelledTopic;

    private final KafkaTemplate<String, TicketEvent> kafkaTemplate;

    public TicketEventProducer(NewTopic ticketApprovedTopic, NewTopic ticketCancelledTopic, KafkaTemplate<String, TicketEvent> kafkaTemplate) {
        this.ticketApprovedTopic = ticketApprovedTopic;
        this.ticketCancelledTopic = ticketCancelledTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, TicketEvent>> produceTicketEvent(Ticket saved) {
        TicketEvent ticketEvent = new TicketEvent();
        ticketEvent.setTicketInfo(new TicketInfo(saved.getTicketId(), saved.getOrderId()));
        Message<TicketEvent> message = MessageBuilder
                .withPayload(ticketEvent)
                .setHeader(KafkaHeaders.TOPIC, ticketApprovedTopic.name())
                .build();
        return kafkaTemplate.send(message);
    }
}
