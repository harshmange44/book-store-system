package com.hrsh.dao;

import com.hrsh.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static List<User> users = new ArrayList<User>();

    public static List<User> getUsers() {
        return users;
    }

    public static void setUsers(List<User> users) {
        UserDao.users = users;
    }

    public static boolean addUser(User user) {
        return users.add(user);
    }

    public static boolean removeUser(String emailId) {
        Optional<User> userOptional = users.stream().filter(user -> user.getEmailId().equalsIgnoreCase(emailId)).findFirst();
        return userOptional.map(user -> users.remove(user)).orElse(false);
    }

    public static Optional<User> findUserByEmailId(String emailId) {
        return users.stream().filter(user -> user.getEmailId().equalsIgnoreCase(emailId)).findFirst();
    }

    public static boolean updateUser(User oldUser, User user) {
        int currUserIndex = users.indexOf(oldUser);
        if (currUserIndex == -1) {
            return false;
        }
        users.set(currUserIndex, user);
        return true;
    }
}
