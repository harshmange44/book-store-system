package com.hrsh.service;

import com.hrsh.model.Cart;
import com.hrsh.model.Order;
import com.hrsh.model.User;

import java.util.List;
import java.util.Optional;

public interface    UserService {
    boolean createUser(User user);
    boolean deleteUser(String emailId);
    User updateUser(String emailId, User user);
    Optional<User> getUser(String emailId);
    List<User> getAllUser();
    Cart getUserCart(String emailId);
    List<Order> getUserOrders(String emailId);
}
