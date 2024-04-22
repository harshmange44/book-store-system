package com.hrsh.service.imp;

import com.hrsh.enums.OrderStatus;
import com.hrsh.enums.PaymentMethod;
import com.hrsh.enums.PaymentStatus;
import com.hrsh.dao.OrderDao;
import com.hrsh.model.Cart;
import com.hrsh.model.Order;
import com.hrsh.model.User;
import com.hrsh.service.OrderService;
import com.hrsh.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

@Slf4j
public class OrderServiceImpl implements OrderService {
    private static UserService userService = UserServiceImpl.getUserServiceInstance();

    private static OrderService orderService = null;

    public static OrderService getOrderServiceInstance() {
        if (Objects.nonNull(orderService)) {
            return orderService;
        }

        orderService = new OrderServiceImpl(userService);
        return orderService;
    }

    public OrderServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean initiateOrder(String emailId, Cart cart, PaymentMethod paymentMethod) {
        Optional<User> optionalUser = userService.getUser(emailId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Order order = new Order();
            order.setId(UUID.randomUUID());
            order.setCart(cart.clone());
            order.setOrderPriority(user.getSubscriptionType().getPriority());
            order.setOrderStatus(OrderStatus.PLACED);
            order.setPaymentMethod(paymentMethod);
            order.setPaymentStatus(PaymentStatus.REQUESTED);
            order.setTotalAmount(cart.getOrderValue());
            order.setOrderedAt(LocalDate.now());

            boolean isOrderInit = OrderDao.initiateOrder(order);
            log.info("Order init: {}, Order: {}, User Email: {}", isOrderInit, order, emailId);

            cart.setOrderValue(0);
            cart.getBookQtyMap().clear();
            user.setCart(cart);
            List<Order> userOrderList = user.getOrderList();
            userOrderList.add(order);
            user.setOrderList(userOrderList);

            return isOrderInit;
        }
        return false;
    }

    @Override
    public boolean executeOrder(String emailId, Cart cart) {
        Order order = OrderDao.getInitOrderPriorityQueue().poll();
        if (Objects.nonNull(order) && order.getPaymentStatus() == PaymentStatus.COMPLETED) {
            order.setOrderStatus(OrderStatus.IN_PROGRESS);
            order.setPayedAt(LocalDate.now());
            return OrderDao.executeOrder(order);
        } else {
            log.info("Couldn't execute order because Payment Status: {}", Objects.nonNull(order) ? order.getPaymentStatus() : null);
        }
        return false;
    }

    @Override
    public boolean cancelOrder(String emailId, UUID orderId) {
        boolean isOrderRemoved = OrderDao.getInitOrderPriorityQueue().removeIf(order -> order.getId().compareTo(orderId) == 0);
        if (!isOrderRemoved) {
            Optional<Order> optionalOrder = OrderDao.getExecutedOrders().stream().filter(order -> order.getId().compareTo(orderId) == 0).findFirst();
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setOrderStatus(OrderStatus.CANCELLED);
                return OrderDao.updateOrder(orderId, order);
            }
        } else {
            return true;
        }
        return false;
    }

    @Override
    public OrderStatus updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        boolean isOrderUpdated = false;
        Optional<Order> optionalOrder = OrderDao.getExecutedOrders().stream().filter(order -> order.getId().compareTo(orderId) == 0).findFirst();
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderStatus(orderStatus);
            isOrderUpdated = OrderDao.updateOrder(orderId, order);
        }
        return isOrderUpdated ? orderStatus : optionalOrder.map(Order::getOrderStatus).orElse(null);
    }

    @Override
    public PaymentStatus updatePaymentStatus(UUID orderId, PaymentStatus paymentStatus) {
        boolean isOrderUpdated = false;
        Optional<Order> optionalOrder;

        optionalOrder = OrderDao.getInitOrderPriorityQueue().stream().filter(order -> order.getId().compareTo(orderId) == 0).findFirst();
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setPaymentStatus(paymentStatus);
        } else {
            optionalOrder = OrderDao.getExecutedOrders().stream().filter(order -> order.getId().compareTo(orderId) == 0).findFirst();
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setPaymentStatus(paymentStatus);
                isOrderUpdated = OrderDao.updateOrder(orderId, order);
            }
        }
        return isOrderUpdated ? paymentStatus : optionalOrder.map(Order::getPaymentStatus).orElse(null);
    }

    @Override
    public Order getOrder(UUID orderId) {
        Optional<Order> optionalOrder = OrderDao.getExecutedOrders().stream().filter(order -> order.getId().compareTo(orderId) == 0).findFirst();
        return optionalOrder.orElse(null);
    }
}
