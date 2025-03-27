package my.learn.basedomain.model.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfo {
    private UUID ticketId;
    private UUID orderId;
}
