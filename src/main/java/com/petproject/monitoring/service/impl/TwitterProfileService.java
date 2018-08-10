package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TwitterProfileRepository;
import com.petproject.monitoring.domain.repository.TargetUserRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.IHelperService;
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
    private IHelperService helperService;
    private TargetUserRepository targetUserRepository;
    private TwitterProfileRepository tpRepository;
    private TwitterUserRepository tuRepository;

    @Override
    @Transactional
    public void add(Long customerId, Long targetUserId, SocialAliasDTO smDTO) {
        TargetUser targetUser =
                targetUserRepository.findByIdAndCustomerId(targetUserId, customerId).orElseThrow(NotFoundException::new);
        saveOrUpdateTwitterUser(smDTO);
        helperService.disableTwitterUserAsTargetIfNeeded(targetUser);
        tpRepository.setAlias(smDTO.getAlias(), targetUserId);
    }

    private void saveOrUpdateTwitterUser(SocialAliasDTO smDTO) {
        Optional<TwitterUser> twitterUserOpt = tuRepository.findByScreenName(smDTO.getAlias());
        if(!twitterUserOpt.isPresent()) {
            try {
                User u = twitter.showUser(smDTO.getAlias());
                tuRepository.save(helperService.getTwitterUserFromAPI(u, true));
            } catch (TwitterException e) {
                log.error(e.getErrorMessage());
            }
        } else if(!twitterUserOpt.get().isTarget()) {
            TwitterUser twitterUser = twitterUserOpt.get();
            twitterUser.setTarget(true);
            tuRepository.save(twitterUser);
        }
    }
}
