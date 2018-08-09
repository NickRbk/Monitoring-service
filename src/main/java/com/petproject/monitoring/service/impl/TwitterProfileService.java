package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.ITwitterProfileService;
import com.petproject.monitoring.web.dto.SocialAliasDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TwitterProfileService implements ITwitterProfileService {

    private Twitter twitter;
    private IEntityAdapterService entityAdapterService;
    private TargetUserRepository targetUserRepository;
    private TwitterProfileRepository smRepository;
    private TwitterUserRepository tuRepository;

    @Override
    @Transactional
    public void add(Long targetUserId, SocialAliasDTO smDTO) {
        targetUserRepository.findById(targetUserId).orElseThrow(NotFoundException::new);
        Optional<TwitterUser> twitterUserOpt = tuRepository.findByScreenName(smDTO.getAlias());
        if(!twitterUserOpt.isPresent()) {
            try {
                User u = twitter.showUser(smDTO.getAlias());
                tuRepository.save(entityAdapterService.getTwitterUserFromAPI(u, true));
            } catch (TwitterException e) {
                log.error(e.getErrorMessage());
            }
        } else if(!twitterUserOpt.get().isTarget()) {
            TwitterUser twitterUser = twitterUserOpt.get();
            twitterUser.setTarget(true);
            tuRepository.save(twitterUser);
        }
        smRepository.setAlias(smDTO.getAlias(), targetUserId);
    }
}
