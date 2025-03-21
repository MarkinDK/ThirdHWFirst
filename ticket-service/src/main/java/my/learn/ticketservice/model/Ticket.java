package my.learn.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    private UUID ticketId;
    private UUID orderId;
    private UUID paymentId;
}
