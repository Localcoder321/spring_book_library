package dev.localcoder.springbooklibrary.controller;

import dev.localcoder.springbooklibrary.dto.book.BookResponse;
import dev.localcoder.springbooklibrary.dto.book.BookRequest;
import dev.localcoder.springbooklibrary.entity.Book;
import dev.localcoder.springbooklibrary.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = bookService.createBook(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        BookResponse response = bookService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        BookResponse updated = bookService.updateBook(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String genre,
            Pageable pageable) {
        Page<BookResponse> page = bookService.search(query, genre, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> books = bookService.getAll();
        return ResponseEntity.ok(books);
    }

    @PostMapping("/clone/{id}")
    public ResponseEntity<BookResponse> cloneBook(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year) {
        BookResponse cloned = bookService.cloneBook(id, title, author, year);
        return ResponseEntity.status(201).body(cloned);
    }
}
