package com.hrsh.utils;

import com.hrsh.exception.UserAlreadyExists;
import com.hrsh.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserUtils {
    public static void validateIfUserAlreadyExists(User user, List<User> users) {
        if (users.stream().anyMatch(userItr -> userItr.getEmailId().equalsIgnoreCase(user.getEmailId()))) {
            log.error("User already exists with the same emailId: {}", user.getEmailId());
            throw new UserAlreadyExists(String.format("User with the emailId: %s already exists", user.getEmailId()));
        }
    }
}
