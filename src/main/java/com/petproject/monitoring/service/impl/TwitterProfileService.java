package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.ITwitterProfileService;
import com.petproject.monitoring.web.dto.SocialAliasDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TwitterProfileService implements ITwitterProfileService {
    private TargetUserRepository targetUserRepository;
    private TwitterProfileRepository smRepository;
    private TwitterUserRepository tuRepository;

    @Override
    @Transactional
    public void add(Long targetUserId, SocialAliasDTO smDTO) {
        targetUserRepository.findById(targetUserId).orElseThrow(NotFoundException::new);
        Optional<TwitterUser> twitterUserOpt = tuRepository.findByScreenName(smDTO.getAlias());
        if(!twitterUserOpt.isPresent()) {
            tuRepository.save(TwitterUser.builder().screenName(smDTO.getAlias()).isTarget(true).build());
        } else if(!twitterUserOpt.get().isTarget()) {
            TwitterUser twitterUser = twitterUserOpt.get();
            twitterUser.setTarget(true);
            tuRepository.save(twitterUser);
        }
        smRepository.setAlias(smDTO.getAlias(), targetUserId);
    }
}
