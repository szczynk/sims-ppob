package com.szczynk.simsppob.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.szczynk.simsppob.model.Balance;

public interface BalanceRepository extends CrudRepository<Balance, Long> {

    @Query("SELECT id, user_id, balance_amount FROM balances WHERE user_id = :id LIMIT 1")
    Optional<Balance> findByUserId(@Param("id") Long id);
}
