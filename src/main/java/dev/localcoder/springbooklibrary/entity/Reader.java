package dev.localcoder.springbooklibrary.entity;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "readers")
@NoArgsConstructor @AllArgsConstructor
public class Reader {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "registered_on", nullable = false)
    private Instant registeredOn =  Instant.now();
    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Rental> rentals = new ArrayList<>();
}
