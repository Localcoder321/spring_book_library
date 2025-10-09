package dev.localcoder.springbooklibrary.repository;

import dev.localcoder.springbooklibrary.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT COUNT(r) FROM Rental r WHERE r.reader.id = :readerId AND r.returnedOn IS NULL")
    long countActiveByReader(@Param("readerId") Long readerId);

    @Query("SELECT r FROM Rental r WHERE r.book.id = :bookId AND r.returnedOn IS NULL")
    List<Rental> findActiveByBook(@Param("bookId") Long bookId);

    @Query("SELECT r FROM Rental r WHERE r.returnedOn IS NULL AND r.dueOn < :now")
    List<Rental> findOverdue(@Param("now") Instant now);

    @Query("""
            SELECT r.book.id, r.book.title, COUNT(r.id)
            FROM Rental r
            WHERE r.takenOn BETWEEN :from AND :to
            GROUP BY r.book.id, r.book.title
            ORDER BY COUNT(r.id) DESC
            """)
    List<Object[]> findPopularBooks(@Param("from") Instant from, @Param("to") Instant to);
}
