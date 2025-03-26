package my.learn.basedomain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.learn.basedomain.model.order.OrderInfo;
import my.learn.basedomain.model.order.OrderStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private OrderInfo orderInfo;
    private OrderStatus status;
}

