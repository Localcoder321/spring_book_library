package dev.localcoder.springbooklibrary.dto.reader;

import lombok.Data;

import java.time.Instant;

@Data
public class ReaderResponse {
    private Long id;
    private String name;
    private String email;
    private Instant registeredOn;
    private List<RentalResponse> rentals;
}
