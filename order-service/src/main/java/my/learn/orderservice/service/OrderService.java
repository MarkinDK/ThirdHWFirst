package my.learn.orderservice.service;

import my.learn.basedomain.dto.OrderRequestDto;
import my.learn.basedomain.dto.OrderResponseDto;
import my.learn.orderservice.model.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> findAll();

    OrderResponseDto createOrder(OrderRequestDto orderInfo);
}
