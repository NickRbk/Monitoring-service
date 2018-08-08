package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.UserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.ITwitterProfileService;
import com.petproject.monitoring.web.dto.SocialAliasDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TwitterProfileService implements ITwitterProfileService {
    private UserRepository userRepository;
    private TwitterProfileRepository smRepository;

    @Override
    public void save(Long targetUserId, SocialAliasDTO smDTO) {
        userRepository.findById(targetUserId).orElseThrow(NotFoundException::new);
        smRepository.setAlias(smDTO.getAlias(), targetUserId);
    }
}
