package dev.localcoder.springbooklibrary.repository;

import dev.localcoder.springbooklibrary.entity.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
    Optional<ReaderEntity> findByEmail(String email);
}
