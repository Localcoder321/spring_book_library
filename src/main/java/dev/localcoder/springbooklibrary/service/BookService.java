package dev.localcoder.springbooklibrary.service;

import dev.localcoder.springbooklibrary.dto.book.BookResponse;
import dev.localcoder.springbooklibrary.dto.book.BookRequest;
import dev.localcoder.springbooklibrary.dto.reader.ReaderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponse createBook(BookRequest request);
    BookResponse getById(Long id);
    BookResponse updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
    Page<BookResponse> search(String q, String genre, Pageable pageable);
    BookResponse cloneBook(Long id, String newTitle, String newAuthor, Integer newYear);
    List<BookResponse> getAll();
}
