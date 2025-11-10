package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.entity.Reader;
import dev.localcoder.springbooklibrary.entity.Rental;
import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import dev.localcoder.springbooklibrary.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReaderLimitHandler extends AbstractRentalHandler {
    private final RentalRepository rentalRepository;
    private static final int MAX_NUMBER_OF_BOOKS = 5;

    @Override
    protected boolean handle(Book book, Reader reader) {
        List<Rental> activeRentals = rentalRepository.findByReaderAndReturnedOnIsNull(reader);
        if(activeRentals.size() >= MAX_NUMBER_OF_BOOKS) {
            throw new IllegalStateException("Reader " + reader.getName() + " already has max allowed books (" + MAX_NUMBER_OF_BOOKS + ")");
        }
        return true;
    }
}
