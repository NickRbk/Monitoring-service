package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.UserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.ISocialMediaService;
import com.petproject.monitoring.web.dto.EntityAdapter;
import com.petproject.monitoring.web.dto.SocialMediaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SocialMediaService implements ISocialMediaService {
    private UserRepository userRepository;
    private SocialMediaRepository smRepository;

    @Override
    public void save(Long userId, SocialMediaDTO smDTO) {
        TargetUser targetUser = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Long id = targetUser.getSocialMedia().getId();
        smRepository.save(EntityAdapter.getSocialMediaFromDTO(id, userId, smDTO));
    }
}
