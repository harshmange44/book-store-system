package com.hrsh.model;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.*;
import java.util.Map;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
public class Cart implements Serializable {
    private UUID id;
    private Map<UUID, Integer> bookQtyMap;

    @PositiveOrZero
    private double orderValue;

    public Cart(UUID id, Map<UUID, Integer> bookQtyMap, double orderValue) {
        this.id = id;
        this.bookQtyMap = bookQtyMap;
        this.orderValue = orderValue;
    }

    public Cart clone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Cart) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
