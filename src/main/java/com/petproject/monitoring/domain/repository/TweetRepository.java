package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Page<Tweet> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM tweets", nativeQuery = true)
    Page<Tweet> findAllOrdered(Pageable pageable);
}
