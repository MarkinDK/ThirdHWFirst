package my.learn.ticketservice.service;

import my.learn.basedomain.model.order.OrderInfo;
import my.learn.ticketservice.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket createTicket(OrderInfo orderInfo);

    List<Ticket> findAll();
}
