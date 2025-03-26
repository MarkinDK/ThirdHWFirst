package my.learn.basedomain.model.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {
    private UUID orderId;
    private UUID paymentId;
    private double price;
}
