package my.learn.ticketservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    private UUID ticketId;
    @Column(unique = true, nullable = false)
    private UUID orderId;
    @Column(unique = true, nullable = false)
    private UUID paymentId;
}
