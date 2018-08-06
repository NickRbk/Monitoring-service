package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.SocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {
    Optional<SocialMedia> findByUserId(Long userId);
}
