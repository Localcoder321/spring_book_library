package dev.localcoder.springbooklibrary.service.impl;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.dto.rental.ReturnBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.TakeBookRequest;
import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.entity.Reader;
import dev.localcoder.springbooklibrary.entity.Rental;
import dev.localcoder.springbooklibrary.exception.ConflictException;
import dev.localcoder.springbooklibrary.exception.NotFoundException;
import dev.localcoder.springbooklibrary.repository.BookRepository;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.repository.RentalRepository;
import dev.localcoder.springbooklibrary.service.RentalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    @Override
    @Transactional
    public RentalResponse takeBook(TakeBookRequest request) {
        Reader reader = readerRepository.findById(request.getReaderId())
                .orElseThrow(() -> new NotFoundException("Reader not found"));
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (!book.isAvailable() || rentalRepository.existsByBookAndReturnedOnIsNull(book)) {
            throw new ConflictException("Book is not available for rental");
        }

        List<Rental> activeRentals = rentalRepository.findByReaderAndReturnedOnIsNull(reader);
        if (activeRentals.size() >= 5) {
            throw new ConflictException("Reader already has 5 active rentals");
        }

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setReader(reader);
        rental.setTakenOn(Instant.now());
        rental.setDueOn(request.getDueOn());
        rentalRepository.save(rental);

        book.setAvailable(false);
        bookRepository.save(book);
        return toResponse(rental);
    }

    @Override
    @Transactional
    public RentalResponse returnBook(ReturnBookRequest book) {
        Rental rental = rentalRepository.findById(book.getRentalId())
                .orElseThrow(() -> new NotFoundException("Rental not found"));
        if(rental.getReturnedOn() != null) {
            throw new ConflictException("Book already returned");
        }
        rental.setReturnedOn(Instant.now());
        rentalRepository.save(rental);

        Book bookRes = rental.getBook();
        bookRes.setAvailable(true);
        bookRepository.save(bookRes);

        return toResponse(rental);
    }

    @Override
    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll().stream().map(
             rental -> new RentalResponse(
                     rental.getId(),
                     rental.getBook().getId(),
                     rental.getBook().getTitle(),
                     rental.getReader().getId(),
                     rental.getReader().getName(),
                     rental.getDueOn(),
                     rental.getTakenOn(),
                     rental.getReturnedOn())
        ).toList();
    }

    private RentalResponse toResponse(Rental rental) {
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
    }
}
