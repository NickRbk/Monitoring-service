package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.web.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<TargetUser> getUsers();
    void add(UserDTO userDTO);
    void update(Long userId, UserDTO userDTO);
    void delete(Long userId);
}
