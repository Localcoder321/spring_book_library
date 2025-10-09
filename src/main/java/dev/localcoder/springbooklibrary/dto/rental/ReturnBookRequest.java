package dev.localcoder.springbooklibrary.dto.rental;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReturnBookRequest {
    @NotNull
    private Long rentalId;
}
