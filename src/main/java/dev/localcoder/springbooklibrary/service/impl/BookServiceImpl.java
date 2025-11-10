package dev.localcoder.springbooklibrary.service.impl;

import dev.localcoder.springbooklibrary.dto.book.BookResponse;
import dev.localcoder.springbooklibrary.dto.book.BookRequest;
import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.exception.NotFoundException;
import dev.localcoder.springbooklibrary.repository.BookRepository;
import dev.localcoder.springbooklibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    @Override
    public BookResponse createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setYear(request.getYear());
        book.setAvailable(true);
        repository.save(book);
        return toResponse(book);
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = repository.findById(id).orElseThrow(() -> new NotFoundException("Book not found" + id));
        return toResponse(book);
    }

    @Override
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = repository.findById(id).orElseThrow(() -> new NotFoundException("Book not found" + id));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setYear(request.getYear());
        repository.save(book);
        return toResponse(book);
    }

    @Override
    public void deleteBook(Long id) {
        if(!repository.existsById(id)) {
            throw new NotFoundException("Book not found" + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Page<BookResponse> search(String q, String genre, Pageable pageable) {
        if(genre != null && !genre.isBlank()) {
            return repository.findByGenreIgnoreCase(genre, pageable).map(this::toResponse);
        } else if (q != null && !q.isBlank()) {
            return repository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(q,q,pageable).map(this::toResponse);
        } else {
            return repository.findAll(pageable).map(this::toResponse);
        }
    }

    @Override
    public BookResponse cloneBook(Long id, String newTitle, String newAuthor, Integer newYear) {
        Book original = repository.findById(id).orElseThrow(() -> new NotFoundException("Book not found " + id));
        Book copy = original.deepCopy();
        if(newTitle != null) copy.setTitle(newTitle);
        if(newAuthor != null) copy.setAuthor(newAuthor);
        if (newYear != null) copy.setYear(newYear);
        copy.setAvailable(true);

        Book saved = repository.save(copy);
        return toResponse(saved);
    }

    @Override
    public List<BookResponse> getAll() {
        return repository.findAll().stream().map(book -> new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getGenre(),
                book.isAvailable()
        )).toList();
    }

    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setGenre(book.getGenre());
        response.setYear(book.getYear());
        response.setAvailable(book.isAvailable());
        return response;
    }
}
