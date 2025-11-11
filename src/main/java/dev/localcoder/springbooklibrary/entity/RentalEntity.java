package dev.localcoder.springbooklibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "rentals")
public class RentalEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private BookEntity book;
    @ManyToOne(optional = false)
    @JoinColumn(name = "reader_id")
    private ReaderEntity reader;
    @Column(name = "taken_on", nullable = false)
    private Instant takenOn;
    @Column(name = "due_on", nullable = false)
    private Instant dueOn;
    @Column(name = "returned_on")
    private Instant returnedOn;
}
