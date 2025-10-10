package dev.localcoder.springbooklibrary.controller;

import dev.localcoder.springbooklibrary.dto.book.BookPopularityResponse;
import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/overdue")
    public List<RentalResponse> getOverdueRentals() {
        return reportService.getOverdueRentals();
    }

    @GetMapping("/popular")
    public List<BookPopularityResponse> getPopularBooks(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        Instant fromInstant = from.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant toInstant = to.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).minusSeconds(1);
        return  reportService.getPopularBooks(fromInstant, toInstant);
    }
}
