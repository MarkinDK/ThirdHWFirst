package my.learn.orderservice.service;

import my.learn.basedomain.dto.OrderRequestDto;
import my.learn.basedomain.dto.OrderResponseDto;
import my.learn.basedomain.model.payment.PaymentInfo;
import my.learn.basedomain.model.payment.PaymentStatus;
import my.learn.basedomain.model.ticket.TicketInfo;
import my.learn.basedomain.model.ticket.TicketStatus;
import my.learn.orderservice.model.OrderEntity;

import java.util.List;

public interface OrderService {
    List<OrderEntity> findAll();

    OrderResponseDto createOrder(OrderRequestDto orderInfo);

    OrderEntity updateOrderAfterPayment(PaymentInfo paymentInfo, PaymentStatus status);

    OrderEntity updateOrderAfterTicketApproval(TicketInfo ticketInfo, TicketStatus status);
}
