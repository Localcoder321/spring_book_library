package dev.localcoder.springbooklibrary.service.impl;

import dev.localcoder.springbooklibrary.dto.book.BookPopularityResponse;
import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.entity.Rental;
import dev.localcoder.springbooklibrary.repository.RentalRepository;
import dev.localcoder.springbooklibrary.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final RentalRepository rentalRepository;

    @Override
    public List<RentalResponse> getOverdueRentals() {
        List<Rental> rentals = rentalRepository.findByReturnedOnIsNullAndDueOnBefore(Instant.now());
        return rentals.stream().map(rental -> {
            RentalResponse response = new RentalResponse();
            response.setId(rental.getId());
            response.setBookId(rental.getBook().getId());
            response.setBookTitle(rental.getBook().getTitle());
            response.setReaderId(rental.getReader().getId());
            response.setReaderName(rental.getReader().getName());
            response.setTakenOn(rental.getTakenOn());
            response.setDueOn(rental.getDueOn());
            response.setReturnedOn(rental.getReturnedOn());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BookPopularityResponse> getPopularBooks(Instant from, Instant to) {
        List<Object[]> data = rentalRepository.findPopularBooks(from, to);
        return data.stream().map(d -> new BookPopularityResponse(
                (Long) d[0],
                (String) d[1],
                ((Number) d[2]).longValue())).collect(Collectors.toList());
    }
}
