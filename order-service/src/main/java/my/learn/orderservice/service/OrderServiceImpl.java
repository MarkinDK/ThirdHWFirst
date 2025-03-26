package my.learn.orderservice.service;

import jakarta.annotation.PostConstruct;
import my.learn.basedomain.dto.OrderRequestDto;
import my.learn.basedomain.dto.OrderResponseDto;
import my.learn.basedomain.event.OrderEvent;
import my.learn.basedomain.model.order.OrderInfo;
import my.learn.basedomain.model.order.OrderStatus;
import my.learn.orderservice.kafka.OrderEventProducer;
import my.learn.orderservice.model.OrderEntity;
import my.learn.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    @PostConstruct
    private void init() {
        orderRepository.saveAll(List.of(
                new OrderEntity(UUID.randomUUID(), 1, OrderStatus.CREATED, UUID.randomUUID(), UUID.randomUUID()),
                new OrderEntity(UUID.randomUUID(), 1, OrderStatus.REJECTED, UUID.randomUUID(), UUID.randomUUID()),
                new OrderEntity(UUID.randomUUID(), 1, OrderStatus.APPROVED, UUID.randomUUID(), UUID.randomUUID())
        ));
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        OrderEntity orderToSave = new OrderEntity();
        orderToSave.setPrice(orderRequestDto.getPrice());
        orderToSave.setStatus(OrderStatus.CREATED);
        OrderEntity savedOrder = orderRepository.save(orderToSave);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(savedOrder.getOrderId());
        orderInfo.setPrice(savedOrder.getPrice());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderInfo(orderInfo);
        orderEvent.setStatus(OrderStatus.CREATED);
        orderEvent.setMessage("Order with id = " + savedOrder.getOrderId() + " created");
        orderEventProducer.sendOrderCreatedEvent(orderEvent);

        return new OrderResponseDto(orderInfo, OrderStatus.CREATED);
    }
}
