package com.szczynk.simsppob.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.szczynk.simsppob.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Iterable<Transaction> findByUserId(Long userId);

    List<Transaction> findByUserId(Long userId, Pageable pageable);

    long countByUserId(Long userId);
}
