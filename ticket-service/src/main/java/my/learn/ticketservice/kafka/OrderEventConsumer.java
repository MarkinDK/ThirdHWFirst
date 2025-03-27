package my.learn.ticketservice.kafka;

import my.learn.basedomain.event.OrderEvent;
import my.learn.ticketservice.service.TicketService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {
    private final TicketService ticketService;

    public OrderEventConsumer(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @KafkaListener(topics =
            "${kafka.topic.name.order.paid}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeOrderEvent(OrderEvent orderEvent) {
        System.out.println();
        System.out.println("HERE");
        System.out.println();
        ticketService.createTicket(orderEvent.getOrderInfo());
    }
}