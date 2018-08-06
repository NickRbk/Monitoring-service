package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.repository.SocialMediaRepository;
import com.petproject.monitoring.domain.repository.UserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.ISocialMediaService;
import com.petproject.monitoring.web.dto.EntityAdapter;
import com.petproject.monitoring.web.dto.SocialMediaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SocialMediaService implements ISocialMediaService {
    private UserRepository userRepository;
    private SocialMediaRepository smRepository;

    @Override
    public void save(Long userId, SocialMediaDTO smDTO) {
        userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Optional<SocialMedia> sm = smRepository.findByUserId(userId);
        Long id = sm.map(SocialMedia::getId).orElse(null);
        smRepository.save(EntityAdapter.getSocialMediaFromDTO(id, userId, smDTO));
    }
}
