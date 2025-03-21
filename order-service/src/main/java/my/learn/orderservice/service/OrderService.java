package my.learn.orderservice.service;

import my.learn.orderservice.model.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> findAll();
}
