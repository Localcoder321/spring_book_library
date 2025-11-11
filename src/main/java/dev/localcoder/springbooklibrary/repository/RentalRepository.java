package dev.localcoder.springbooklibrary.repository;

import dev.localcoder.springbooklibrary.entity.BookEntity;
import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import dev.localcoder.springbooklibrary.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface RentalRepository extends JpaRepository<RentalEntity, Long> {
    @Query("SELECT COUNT(r) FROM RentalEntity r WHERE r.reader.id = :readerId AND r.returnedOn IS NULL")
    long countActiveByReader(@Param("readerId") Long readerId);

    @Query("SELECT r FROM RentalEntity r WHERE r.book.id = :bookId AND r.returnedOn IS NULL")
    List<RentalEntity> findActiveByBook(@Param("bookId") Long bookId);

    @Query("SELECT r FROM RentalEntity r WHERE r.returnedOn IS NULL AND r.dueOn < :now")
    List<RentalEntity> findOverdue(@Param("now") Instant now);

    List<RentalEntity> findByReaderAndReturnedOnIsNull(ReaderEntity reader);

    boolean existsByBookAndReturnedOnIsNull(BookEntity book);

    @Query("""
            SELECT r.book.id, r.book.title, COUNT(r.id)
            FROM RentalEntity r
            WHERE r.takenOn BETWEEN :from AND :to
            GROUP BY r.book.id, r.book.title
            ORDER BY COUNT(r.id) DESC
            """)
    List<Object[]> findPopularBooks(@Param("from") Instant from, @Param("to") Instant to);

    List<RentalEntity> findByReturnedOnIsNullAndDueOnBefore(Instant now);
}
