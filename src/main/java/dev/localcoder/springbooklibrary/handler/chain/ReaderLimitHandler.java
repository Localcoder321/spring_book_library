package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.entity.Reader;
import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import org.springframework.stereotype.Component;

@Component
public class ReaderLimitHandler extends AbstractRentalHandler {
    private static final int MAX_NUMBER_OF_BOOKS = 5;

    @Override
    protected boolean handle(Book book, Reader reader) {
        if(reader.getRentals() != null && reader.getRentals().size() >= MAX_NUMBER_OF_BOOKS) {
            throw new IllegalStateException("Reader " + reader.getName() + " already has max allowed books (" + MAX_NUMBER_OF_BOOKS + ")");
        }
        return true;
    }
}
