package my.learn.orderservice.service;

import my.learn.basedomain.dto.OrderRequestDto;
import my.learn.basedomain.dto.OrderResponseDto;
import my.learn.basedomain.event.PaymentEvent;
import my.learn.basedomain.model.order.OrderInfo;
import my.learn.basedomain.model.order.OrderStatus;
import my.learn.basedomain.model.payment.PaymentInfo;
import my.learn.basedomain.model.payment.PaymentStatus;
import my.learn.orderservice.kafka.OrderEventProducer;
import my.learn.orderservice.model.OrderEntity;
import my.learn.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
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

        orderEventProducer.produceOrderCreatedEvent(savedOrder);

        return new OrderResponseDto(
                new OrderInfo(savedOrder.getOrderId(), savedOrder.getPrice()),
                OrderStatus.CREATED
        );
    }

    @Transactional
    @Override
    public OrderEntity updateOrderAfterPayment(PaymentInfo paymentInfo, PaymentStatus status) {
        OrderEntity orderToSave = constructUpdatedOrderToSave(paymentInfo, status);
        OrderEntity updated = orderRepository.save(orderToSave);
        if (updated.getStatus().equals(OrderStatus.PAID)) {
            orderEventProducer.produceOrderPaidEvent(updated);
        }
        return updated;
    }

    private OrderEntity constructUpdatedOrderToSave(PaymentInfo paymentInfo, PaymentStatus status) {
        OrderEntity order = orderRepository
                .findById(paymentInfo.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setPaymentId(paymentInfo.getPaymentId());
        switch (status) {
            case APPROVED -> {
                order.setStatus(OrderStatus.PAID);
            }
            case REJECTED -> {
                order.setStatus(OrderStatus.REJECTED);
            }
            case REFUNDED -> order.setStatus(OrderStatus.CANCELLED);
        }
        return order;
    }

}
