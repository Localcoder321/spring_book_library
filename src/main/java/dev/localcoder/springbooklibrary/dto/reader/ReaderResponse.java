package dev.localcoder.springbooklibrary.dto.reader;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderResponse {
    private Long id;
    private String name;
    private String email;
    private Instant registeredOn;
    private List<RentalResponse> rentals;
    private Set<String> roles;

    public static ReaderResponse from(ReaderEntity entity) {
        return new ReaderResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getRegisteredOn(),
                entity.getRentals().stream()
                        .map(RentalResponse::from)
                        .collect(Collectors.toList()),
                entity.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toSet())
        );
    }
}
