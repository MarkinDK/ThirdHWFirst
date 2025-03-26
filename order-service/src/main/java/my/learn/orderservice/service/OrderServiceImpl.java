package my.learn.orderservice.service;

import jakarta.annotation.PostConstruct;
import my.learn.basedomain.dto.OrderRequestDto;
import my.learn.basedomain.dto.OrderResponseDto;
import my.learn.basedomain.event.OrderEvent;
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

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(savedOrder.getOrderId());
        orderInfo.setPrice(savedOrder.getPrice());

        sendOrderEvent(orderInfo, savedOrder);

        return new OrderResponseDto(orderInfo, OrderStatus.CREATED);
    }

    @Transactional
    @Override
    public OrderEntity updateOrderAfterPayment(PaymentInfo paymentInfo, PaymentStatus status) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(paymentInfo.getOrderId());
        if (optionalOrder.isPresent()) {
            OrderEntity order = optionalOrder.get();
            order.setPaymentId(paymentInfo.getPaymentId());
            switch (status) {
                case APPROVED -> order.setStatus(OrderStatus.APPROVED);
                case REJECTED -> order.setStatus(OrderStatus.REJECTED);
                case REFUNDED -> order.setStatus(OrderStatus.CANCELLED);
            }
            return orderRepository.save(order);
        } else throw new RuntimeException("Order not found");

    }

    private void sendOrderEvent(OrderInfo orderInfo, OrderEntity savedOrder) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderInfo(orderInfo);
        orderEvent.setStatus(OrderStatus.CREATED);
        orderEvent.setMessage("\nOrder with id = " + savedOrder.getOrderId() + " created\n");
        orderEventProducer.produceOrderCreatedEvent(orderEvent);
    }
}
