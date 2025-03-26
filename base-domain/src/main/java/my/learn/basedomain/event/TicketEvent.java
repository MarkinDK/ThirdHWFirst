package my.learn.basedomain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.learn.basedomain.model.ticket.TicketInfo;
import my.learn.basedomain.model.ticket.TicketStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEvent {
    private TicketInfo ticketInfo;
    private TicketStatus status;
    private String message;
}
