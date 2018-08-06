package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.User;
import com.petproject.monitoring.domain.repository.UserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IUserService;
import com.petproject.monitoring.web.dto.EntityAdapter;
import com.petproject.monitoring.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void add(UserDTO userDTO) {
        userRepository.save(EntityAdapter.getUserFromDTO(null, userDTO));
    }

    @Override
    public void update(Long userId, UserDTO userDTO) {
        if(userRepository.findById(userId).isPresent()) {
            userRepository.save(EntityAdapter.getUserFromDTO(userId, userDTO));
        } else throw new NotFoundException();
    }

    @Override
    public void delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        userRepository.delete(user);
    }
}
