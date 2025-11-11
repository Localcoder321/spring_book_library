package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.entity.BookEntity;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import org.springframework.stereotype.Component;

@Component
public class BookAvailableHandler extends AbstractRentalHandler {
    @Override
    protected boolean handle(BookEntity book, ReaderEntity reader) {
        if(!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }
        return true;
    }
}
