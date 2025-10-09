package dev.localcoder.springbooklibrary.repository;

import dev.localcoder.springbooklibrary.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByGenreIgnoreCase(String genre, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);
}
