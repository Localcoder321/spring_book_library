package dev.localcoder.springbooklibrary.service.impl;

import dev.localcoder.springbooklibrary.dto.rental.RentalResponse;
import dev.localcoder.springbooklibrary.dto.rental.ReturnBookRequest;
import dev.localcoder.springbooklibrary.dto.rental.TakeBookRequest;
import dev.localcoder.springbooklibrary.entity.BookEntity;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import dev.localcoder.springbooklibrary.entity.RentalEntity;
import dev.localcoder.springbooklibrary.exception.ConflictException;
import dev.localcoder.springbooklibrary.exception.NotFoundException;
import dev.localcoder.springbooklibrary.handler.chain.RentalValidationChain;
import dev.localcoder.springbooklibrary.repository.BookRepository;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.repository.RentalRepository;
import dev.localcoder.springbooklibrary.service.RentalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service("rentalServiceImpl")
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final RentalValidationChain rentalValidationChain;

    @Override
    @Transactional
    public RentalResponse takeBook(TakeBookRequest request) {
        ReaderEntity reader = readerRepository.findById(request.getReaderId())
                .orElseThrow(() -> new NotFoundException("Reader not found"));
        BookEntity book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found"));

        rentalValidationChain.getChain().check(book, reader);

        RentalEntity rental = new RentalEntity();
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
        RentalEntity rental = rentalRepository.findById(book.getRentalId())
                .orElseThrow(() -> new NotFoundException("Rental not found"));
        if(rental.getReturnedOn() != null) {
            throw new ConflictException("Book already returned");
        }
        rental.setReturnedOn(Instant.now());
        rentalRepository.save(rental);

        BookEntity bookRes = rental.getBook();
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

    private RentalResponse toResponse(RentalEntity rental) {
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
