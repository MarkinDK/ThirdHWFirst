package my.learn.orderservice.kafka;

import my.learn.basedomain.event.PaymentEvent;
import my.learn.basedomain.event.TicketEvent;
import my.learn.orderservice.service.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TicketEventConsumer {
    private final OrderService orderService;

    public TicketEventConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics =
            "${kafka.topic.name.ticket.approved}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeTicketEvent(TicketEvent ticketEvent) {
        orderService.updateOrderAfterTicketApproval(ticketEvent.getTicketInfo(), ticketEvent.getStatus());
    }
}
