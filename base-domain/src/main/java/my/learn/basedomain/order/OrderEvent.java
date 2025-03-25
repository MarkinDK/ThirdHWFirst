package my.learn.basedomain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private OrderInfo order;
    private OrderStatus status;
    private String message;

}
