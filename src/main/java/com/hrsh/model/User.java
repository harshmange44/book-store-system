package com.hrsh.model;

import com.hrsh.enums.Role;
import com.hrsh.enums.SubscriptionType;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
public class User implements Serializable {
    private UUID id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty
    private String emailId;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$\n")
    @Length(min = 8, max = 20)
    private String password;

    private Role role;

    @PastOrPresent
    private LocalDate createdAt;

    private Cart cart;

    private SubscriptionType subscriptionType;

    private List<Order> orderList;
}
