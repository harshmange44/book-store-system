package com.hrsh.service;

import com.hrsh.enums.OrderStatus;
import com.hrsh.enums.PaymentMethod;
import com.hrsh.enums.PaymentStatus;
import com.hrsh.model.Cart;
import com.hrsh.model.Order;

import java.util.UUID;

public interface OrderService {
    boolean initiateOrder(String emailId, Cart cart, PaymentMethod paymentMethod);
    boolean executeOrder(String emailId, Cart cart);
    boolean cancelOrder(String emailId, UUID orderId);
    OrderStatus updateOrderStatus(UUID orderId, OrderStatus orderStatus);

    PaymentStatus updatePaymentStatus(UUID orderId, PaymentStatus paymentStatus);

    Order getOrder(UUID orderId);
}
