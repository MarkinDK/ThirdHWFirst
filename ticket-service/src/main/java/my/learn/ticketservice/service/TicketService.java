package my.learn.ticketservice.service;

import jakarta.annotation.PostConstruct;
import my.learn.ticketservice.model.Ticket;
import my.learn.ticketservice.repository.TicketRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @PostConstruct private void init() {
        ticketRepository.saveAll(List.of(
                new Ticket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new Ticket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new Ticket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())));
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
