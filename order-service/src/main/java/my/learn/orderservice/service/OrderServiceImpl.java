package my.learn.orderservice.service;

import jakarta.annotation.PostConstruct;
import my.learn.orderservice.model.OrderEntity;
import my.learn.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostConstruct
    private void init() {
        orderRepository.saveAll(List.of(
                new OrderEntity(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID()),
                new OrderEntity(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID()),
                new OrderEntity(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()), UUID.randomUUID())
        ));
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }
}
