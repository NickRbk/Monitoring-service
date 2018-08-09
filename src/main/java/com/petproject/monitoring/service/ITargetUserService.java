package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.web.dto.TargetUserDTO;

import java.util.List;

public interface ITargetUserService {
    List<TargetUser> getUsers();
    void add(TargetUserDTO targetUserDTO);
    void update(Long userId, TargetUserDTO targetUserDTO);
    void delete(Long userId);
}
