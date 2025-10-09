package dev.localcoder.springbooklibrary.dto.reader;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ReaderResponse {
    private Long id;
    private String name;
    private String email;
    private Instant registeredOn;
    private List<RentalResponse> rentals;
}
