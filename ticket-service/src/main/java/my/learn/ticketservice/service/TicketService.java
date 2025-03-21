package my.learn.ticketservice.service;

import my.learn.ticketservice.model.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> findAll();
}
