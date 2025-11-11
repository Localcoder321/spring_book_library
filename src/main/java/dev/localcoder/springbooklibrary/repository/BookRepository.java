package dev.localcoder.springbooklibrary.repository;

import dev.localcoder.springbooklibrary.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findByGenreIgnoreCase(String genre, Pageable pageable);
    Page<BookEntity> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author, Pageable pageable);
}
