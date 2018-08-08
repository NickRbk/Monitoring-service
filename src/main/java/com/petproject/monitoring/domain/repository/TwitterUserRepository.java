package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TwitterUserRepository extends JpaRepository<TwitterUser, Long> {
    Optional<TwitterUser> findByScreenName(String screenName);
}
