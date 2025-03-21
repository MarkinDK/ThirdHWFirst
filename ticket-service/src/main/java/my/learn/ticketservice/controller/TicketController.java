package my.learn.ticketservice.controller;

import my.learn.ticketservice.model.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        return ResponseEntity.ok().body(List.of(
                new Ticket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new Ticket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()),
                new Ticket(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID())));
    }
}
