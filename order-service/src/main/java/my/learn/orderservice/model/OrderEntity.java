package my.learn.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.learn.basedomain.model.order.OrderStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderEntity {
    @Id
    private UUID orderId;
    private double price;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    private UUID paymentId;
    private UUID ticketId;


//    @Column(nullable = false)
//    @ElementCollection
//    private List<UUID> ticketIdList;
//    @Column(nullable = false, unique = true)
//    private UUID paymentId;
}
