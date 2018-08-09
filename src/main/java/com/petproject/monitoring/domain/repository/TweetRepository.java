package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
