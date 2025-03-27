package my.learn.ticketservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.learn.basedomain.model.ticket.TicketStatus;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    private UUID ticketId;
    private UUID orderId;
    private TicketStatus status;
}
