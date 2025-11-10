package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.entity.Reader;
import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import org.springframework.stereotype.Component;

@Component
public class BookAvailableHandler extends AbstractRentalHandler {
    @Override
    protected boolean handle(Book book, Reader reader) {
        if(!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }
        return true;
    }
}
