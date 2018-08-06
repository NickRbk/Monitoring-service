package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.User;
import com.petproject.monitoring.web.dto.UserDTO;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    void add(UserDTO userDTO);
    void update(Long userId, UserDTO userDTO);
    void delete(Long userId);
}
