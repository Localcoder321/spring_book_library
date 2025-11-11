package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.entity.BookEntity;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import dev.localcoder.springbooklibrary.exception.NotFoundException;
import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import dev.localcoder.springbooklibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookExistsHandler extends AbstractRentalHandler {
    private final BookRepository bookRepository;

    @Override
    protected boolean handle(BookEntity book, ReaderEntity reader) {
        if(book == null || !bookRepository.existsById(book.getId())) {
            throw new NotFoundException("Book with id " + (book != null ? book.getId():null) + " not found");
        }
        return true;
    }
}
