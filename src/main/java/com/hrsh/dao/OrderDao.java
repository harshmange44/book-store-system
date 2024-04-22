package com.hrsh.dao;

import com.hrsh.model.Order;

import java.util.*;

public class OrderDao {
    private static List<Order> executedOrders = new ArrayList<>();

    public static PriorityQueue<Order> getInitOrderPriorityQueue() {
        return initOrderPriorityQueue;
    }

    public static void setInitOrderPriorityQueue(PriorityQueue<Order> initOrderPriorityQueue) {
        OrderDao.initOrderPriorityQueue = initOrderPriorityQueue;
    }

    private static PriorityQueue<Order> initOrderPriorityQueue = new PriorityQueue<>((o1, o2) -> {
        // if order priority is same, compare based on the date-time
        if (o1.getOrderPriority() == o2.getOrderPriority()) {
            if (Objects.nonNull(o1.getPayedAt()) && Objects.nonNull(o2.getPayedAt())) {
                return o1.getPayedAt().compareTo(o2.getPayedAt());
            }

            return o1.getOrderedAt().compareTo(o2.getOrderedAt());
        }
        return o1.getOrderPriority() - o2.getOrderPriority();
    });

    public static List<Order> getExecutedOrders() {
        return executedOrders;
    }

    public static void setExecutedOrders(List<Order> executedOrders) {
        OrderDao.executedOrders = executedOrders;
    }

    public static boolean initiateOrder(Order order) {
        return initOrderPriorityQueue.add(order);
    }

    public static boolean executeOrder(Order order) {
        return executedOrders.add(order);
    }

    public static boolean updateOrder(UUID orderId, Order newOrder) {
        Optional<Order> currOrder = executedOrders.stream().filter(order -> order.getId().compareTo(orderId) == 0).findFirst();
        if (currOrder.isPresent()) {
            int currOrderIndex = executedOrders.indexOf(currOrder.get());
            executedOrders.set(currOrderIndex, newOrder);
            return true;
        }

        return false;
    }
}
