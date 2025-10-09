package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.book.BookResponse;
import dev.localcoder.springbooklibrary.dto.book.CreateBookRequest;
import dev.localcoder.springbooklibrary.dto.book.UpdateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(CreateBookRequest request);
    BookResponse getById(Long id);
    BookResponse updateBook(Long id, UpdateBookRequest request);
    void deleteBook(Long id);
    Page<BookResponse> search(String q, String genre, Pageable pageable);
}
