package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
