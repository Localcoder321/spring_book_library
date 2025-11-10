package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalValidationChain {
    private final BookExistsHandler bookExistsHandler;
    private final BookAvailableHandler bookAvailableHandler;
    private final ReaderExistsHandler readerExistsHandler;
    private final ReaderLimitHandler readerLimitHandler;

    public AbstractRentalHandler getChain() {
        bookExistsHandler
                .linkWith(bookAvailableHandler)
                .linkWith(readerExistsHandler)
                .linkWith(readerLimitHandler);
        return bookExistsHandler;
    }
}
