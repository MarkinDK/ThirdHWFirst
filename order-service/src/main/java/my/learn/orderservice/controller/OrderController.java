package my.learn.orderservice.controller;

import my.learn.basedomain.order.OrderEvent;
import my.learn.basedomain.order.OrderInfo;
import my.learn.basedomain.order.OrderStatus;
import my.learn.orderservice.kafka.OrderProducer;
import my.learn.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    public OrderController(OrderService orderService, OrderProducer orderProducer) {
        this.orderService = orderService;
        this.orderProducer = orderProducer;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createOrder() {
        orderProducer.sendOrderEvent(
                new OrderEvent(
                        new OrderInfo(UUID.randomUUID(), 1000),
                        OrderStatus.CREATED,
                        "Some order created in kafka"
                ));
        return ResponseEntity.ok().build();
    }
}
