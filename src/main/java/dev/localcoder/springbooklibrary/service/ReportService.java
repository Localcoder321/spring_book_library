package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.book.BookPopularityResponse;
import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;

import java.time.Instant;
import java.util.List;

public interface ReportService {
    List<RentalResponse> getOverdueRentals();
    List<BookPopularityResponse> getPopularBooks(Instant from, Instant to);
}
