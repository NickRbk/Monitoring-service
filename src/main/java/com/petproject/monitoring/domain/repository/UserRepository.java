package com.petproject.monitoring.domain.repository;

import com.petproject.monitoring.domain.model.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<TargetUser, Long> {
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET social_media_id=:smId WHERE id=:userId", nativeQuery = true)
    void setSocialMediaRef(@Param("smId") Long smId, @Param("userId") Long userId);
}
