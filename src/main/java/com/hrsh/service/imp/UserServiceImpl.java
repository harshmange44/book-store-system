package com.hrsh.service.imp;

import com.hrsh.dao.UserDao;
import com.hrsh.model.Cart;
import com.hrsh.model.Order;
import com.hrsh.model.User;
import com.hrsh.service.CartService;
import com.hrsh.service.UserService;
import com.hrsh.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {
    private static CartService cartService = CartServiceImpl.getCartServiceInstance();

    private static UserService userService = null;

    public static UserService getUserServiceInstance() {
        if (Objects.nonNull(userService)) {
            return userService;
        }

        userService = new UserServiceImpl(cartService);
        return userService;
    }

    public UserServiceImpl(CartService cartService) {
        this.cartService = cartService;
    }

    public boolean createUser(User user) {
        UserUtils.validateIfUserAlreadyExists(user, UserDao.getUsers());
        Cart cart = cartService.createNewCart();
        user.setCart(cart);
        user.setOrderList(new ArrayList<>());
        user.setCreatedAt(LocalDate.now());
        return UserDao.addUser(user);
    }

    public boolean deleteUser(String emailId) {
        if (getUser(emailId).isPresent()) {
            return UserDao.removeUser(emailId);
        }
        return false;
    }

    public User updateUser(String emailId, User user) {
        boolean isUserUpdated = false;
        Optional<User> optionalUser = getUser(emailId);
        User oldUser = null;
        if (optionalUser.isPresent()) {
            oldUser = optionalUser.get();
            isUserUpdated = UserDao.updateUser(oldUser, user);

            log.info("User updated: {}", isUserUpdated);
        }
        return isUserUpdated ? user : oldUser;
    }

    public Optional<User> getUser(String emailId) {
        return UserDao.findUserByEmailId(emailId);
    }

    @Override
    public List<User> getAllUser() {
        return UserDao.getUsers();
    }

    @Override
    public Cart getUserCart(String emailId) {
        Optional<User> optionalUser = getUser(emailId);
        return optionalUser.map(User::getCart).orElse(null);
    }

    @Override
    public List<Order> getUserOrders(String emailId) {
        Optional<User> optionalUser = getUser(emailId);
        return optionalUser.map(User::getOrderList).orElse(null);
    }
}
