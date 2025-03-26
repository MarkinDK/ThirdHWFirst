package my.learn.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.learn.basedomain.model.payment.PaymentStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;
    private UUID orderId;
    private UUID ticketId;
    private double price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
