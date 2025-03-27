package my.learn.ticketservice.service;

import my.learn.basedomain.model.order.OrderInfo;
import my.learn.basedomain.model.ticket.TicketStatus;
import my.learn.ticketservice.kafka.TicketEventProducer;
import my.learn.ticketservice.model.Ticket;
import my.learn.ticketservice.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketEventProducer ticketEventProducer;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketEventProducer ticketEventProducer) {
        this.ticketRepository = ticketRepository;
        this.ticketEventProducer = ticketEventProducer;
    }

    @Override
    public Ticket createTicket(OrderInfo orderInfo) {
        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatus.APPROVED);
        ticket.setOrderId(orderInfo.getOrderId());
        Ticket saved = ticketRepository.save(ticket);
        ticketEventProducer.produceTicketEvent(saved);
        return saved;
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
