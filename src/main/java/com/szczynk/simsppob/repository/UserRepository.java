package com.szczynk.simsppob.repository;

import org.springframework.data.repository.CrudRepository;

import com.szczynk.simsppob.model.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
