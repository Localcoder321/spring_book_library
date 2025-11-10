package dev.localcoder.springbooklibrary.handler.chain;

import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.entity.Reader;
import dev.localcoder.springbooklibrary.handler.AbstractRentalHandler;
import dev.localcoder.springbooklibrary.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReaderExistsHandler extends AbstractRentalHandler {
    private final ReaderRepository readerRepository;

    @Override
    protected boolean handle(Book book, Reader reader) {
        if(reader == null || !readerRepository.existsById(reader.getId())) {
            throw new RuntimeException("Reader not found with id " + (reader !=null ? reader.getId():null));
        }
        return true;
    }
}
