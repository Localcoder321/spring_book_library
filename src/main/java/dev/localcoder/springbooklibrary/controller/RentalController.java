package dev.localcoder.springbooklibrary.controller;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.dto.rental.ReturnBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.TakeBookRequest;
import dev.localcoder.springbooklibrary.service.RentalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/take")
    public ResponseEntity<RentalResponse> takeBook(@Valid @RequestBody TakeBookRequest request) {
        RentalResponse response = rentalService.takeBook(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/return")
    public ResponseEntity<RentalResponse> returnBook(@Valid @RequestBody ReturnBookRequest request) {
        RentalResponse response = rentalService.returnBook(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RentalResponse>> getListOfRentals() {
        List<RentalResponse> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }
}
