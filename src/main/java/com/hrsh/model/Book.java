package com.hrsh.model;

import com.hrsh.enums.Genre;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.io.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@Setter
@ToString
public class Book implements Serializable {
    private UUID id;

    @NotNull(message = "title cannot be null")
    @NotEmpty
    private String title;

    @NonNull
    @NotEmpty
    @Length(min = 4, max = 13)
    private String isbn;

    private User author;
    private Genre genre;

    @PositiveOrZero
    private double price;

    private Publisher publisher;

    private LocalDate publishedAt;

    @Min(0)
    @Max(5)
    private double rating = 0;

    @PositiveOrZero
    private int copiesAvailable;

    public Book clone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Book) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
