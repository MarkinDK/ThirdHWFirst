package my.learn.orderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderEntity {
    @Id
    private UUID orderId;
    @Column(nullable = false)
    @ElementCollection
    private List<UUID> ticketIdList;
    @Column(nullable = false, unique = true)
    private UUID paymentId;
}
