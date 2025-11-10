package dev.localcoder.springbooklibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    private int year;
    private String genre;
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    public Book deepCopy() {
        Book copy = new Book();
        copy.setTitle(this.title);
        copy.setAuthor(this.author);
        copy.setYear(this.year);
        copy.setGenre(this.genre);
        copy.setAvailable(true);
        return copy;
    }
}
