package dev.localcoder.springbooklibrary.dto.rental;

import dev.localcoder.springbooklibrary.entity.RentalEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long readerId;
    private String readerName;
    private Instant takenOn;
    private Instant dueOn;
    private Instant returnedOn;

    public static RentalResponse from(RentalEntity entity) {
        return new RentalResponse(
                entity.getId(),
                entity.getBook().getId(),
                entity.getBook().getTitle(),
                entity.getReader().getId(),
                entity.getReader().getName(),
                entity.getTakenOn(),
                entity.getDueOn(),
                entity.getReturnedOn()
        );
    }
}
