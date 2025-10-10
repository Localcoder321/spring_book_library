package dev.localcoder.springbooklibrary.dto.rental;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
public class TakeBookRequest {
    @NotNull
    private Long readerId;
    @NotNull
    private Long bookId;
    @NotNull
    @Future
    private Instant dueOn;
}
