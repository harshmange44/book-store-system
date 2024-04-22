package com.hrsh.mockdata;

import com.hrsh.enums.Role;
import com.hrsh.enums.SubscriptionType;
import com.hrsh.model.User;

import java.util.Objects;
import java.util.UUID;

public class MockUserData {
    private static User adminUser = null;

    private static User freeUser = null;

    private static User premiumUser = null;

    private static User authorUser = null;

    public static User getAdminUser() {
        if (Objects.nonNull(adminUser)) {
            return adminUser;
        }

        return initAdminUser();
    }

    public static User getFreeUser() {
        if (Objects.nonNull(freeUser)) {
            return freeUser;
        }

        return initFreeUser();
    }

    public static User getPremiumUser() {
        if (Objects.nonNull(premiumUser)) {
            return premiumUser;
        }

        return initPremiumUser();
    }

    public static User getAuthorUser() {
        if (Objects.nonNull(authorUser)) {
            return authorUser;
        }

        return initAuthorUser();
    }

    public static User initAdminUser() {
        adminUser = new User();
        adminUser.setId(UUID.randomUUID());
        adminUser.setName("Admin");
        adminUser.setEmailId("admin@gmail.com");
        adminUser.setPassword("Admin@Passwd");
        adminUser.setRole(Role.ADMIN);
        adminUser.setSubscriptionType(SubscriptionType.PREMIUM);
        return adminUser;
    }

    public static User initFreeUser() {
        freeUser = new User();
        freeUser.setId(UUID.randomUUID());
        freeUser.setName("Free User");
        freeUser.setEmailId("freeuser@gmail.com");
        freeUser.setPassword("Free@Passwd");
        freeUser.setRole(Role.BASIC);
        freeUser.setSubscriptionType(SubscriptionType.FREE);
        return freeUser;
    }

    public static User initPremiumUser() {
        premiumUser = new User();
        premiumUser.setId(UUID.randomUUID());
        premiumUser.setName("Premium User");
        premiumUser.setEmailId("premiumuser@gmail.com");
        premiumUser.setPassword("Premium@Passwd");
        premiumUser.setRole(Role.BASIC);
        premiumUser.setSubscriptionType(SubscriptionType.PREMIUM);
        return premiumUser;
    }

    public static User initAuthorUser() {
        authorUser = new User();
        authorUser.setId(UUID.randomUUID());
        authorUser.setName("Author User");
        authorUser.setEmailId("authoruser@gmail.com");
        authorUser.setPassword("Author@Passwd");
        authorUser.setRole(Role.AUTHOR);
        authorUser.setSubscriptionType(SubscriptionType.PREMIUM);
        return authorUser;
    }
}
