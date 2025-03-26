package my.learn.basedomain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.learn.basedomain.model.payment.PaymentInfo;
import my.learn.basedomain.model.payment.PaymentStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private PaymentInfo paymentInfo;
    private PaymentStatus status;
    private String message;
}
