package com.hrsh.model;

import com.hrsh.enums.OrderStatus;
import com.hrsh.enums.PaymentMethod;
import com.hrsh.enums.PaymentStatus;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
public class Order implements Serializable {
    private UUID id;
    private Cart cart;

    @PositiveOrZero
    private double totalAmount;
    private PaymentMethod paymentMethod;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

    private int orderPriority;

    private LocalDate orderedAt;
    private LocalDate payedAt = null;
}
