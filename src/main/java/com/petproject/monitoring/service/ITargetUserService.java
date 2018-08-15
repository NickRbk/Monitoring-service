package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.web.dto.TargetUserDTO;

import java.util.List;

public interface ITargetUserService {
    List<TargetUser> getUsersByCustomerId(Long customerId);
    void add(Long customerId, TargetUserDTO targetUserDTO);
    void update(Long customerId, Long targetUserId, TargetUserDTO targetUserDTO);
    void delete(TargetUser targetUser);
}
