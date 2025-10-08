package dev.localcoder.springbooklibrary.repository;

import dev.localcoder.springbooklibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
