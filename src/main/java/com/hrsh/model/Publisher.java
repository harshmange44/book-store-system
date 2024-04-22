package com.hrsh.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
public class Publisher implements Serializable {
    private UUID id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty
    private String name;
}
